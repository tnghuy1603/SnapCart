package com.snapcart.order_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapcart.order_service.constant.OrderLineEvent;
import com.snapcart.order_service.constant.OrderLineStatus;
import com.snapcart.order_service.dto.mapper.OrderMapper;
import com.snapcart.order_service.dto.request.CheckOutRequest;
import com.snapcart.order_service.dto.request.FilterOrderRequest;
import com.snapcart.order_service.dto.request.ReserveStockRequest;
import com.snapcart.order_service.dto.response.*;
import com.snapcart.order_service.entity.*;
import com.snapcart.order_service.exception.OrderServiceException;
import com.snapcart.order_service.repository.CartRepository;
import com.snapcart.order_service.repository.OrderLineRepository;
import com.snapcart.order_service.repository.OrderRepository;
import com.snapcart.order_service.service.OrderService;
import com.snapcart.order_service.service.ProductServiceClient;
import com.snapcart.order_service.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final StateMachineFactory<OrderLineStatus, OrderLineEvent> factory;
    private final UserServiceClient userServiceClient;
    private final OrderLineRepository orderLineRepository;
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public OrderResponse createOrder(CheckOutRequest request) {
        CartEntity cartEntity = cartRepository.findByBuyerId(request.getBuyerId());
        if (cartEntity == null) {
            throw OrderServiceException.buildBadRequest(
                    "Not found any cart for user" + request.getBuyerId());
        }
        BigDecimal shippingFee = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO.add(shippingFee);
        List<String> productIds = request.getCartLines()
                .stream()
                .map(CartLine::getProductId)
                .toList();
        //Check product info
        List<ProductInfo> productInfoList = productServiceClient.getProductInfoList(productIds).getData();
        Map<String, ProductInfo> productInfoMap = productInfoList.stream()
                .collect(Collectors.toMap(ProductInfo::getId, p -> p));
        //Check buyer info
        UserInfo buyerInfo = userServiceClient.getUserInfo(request.getBuyerId()).getData();

        List<OrderLineEntity> orderLineEntities = new ArrayList<>();
        OrderStatusHistory initialHistory = OrderStatusHistory.builder()
                .toStatus(OrderLineStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .build();
        for (CartLine cartLine : request.getCartLines()) {
            ProductInfo productInfo = productInfoMap.get(cartLine.getProductId());
            if (productInfo.getStock() < cartLine.getQuantity()) {
                throw OrderServiceException.buildBadRequest("Not enough stock for cart " + cartLine.getProductId());
            }
            BigDecimal subtotalLine = productInfo.getPrice().multiply(new BigDecimal(cartLine.getQuantity()));
            totalAmount = totalAmount.add(subtotalLine);
            OrderLineEntity orderLine = OrderLineEntity.builder()
                    .productId(cartLine.getProductId())
                    .productName(productInfo.getName())
                    .quantity(cartLine.getQuantity())
                    .price(productInfo.getPrice())
                    .subtotal(subtotalLine)
                    .images(productInfo.getImages())
                    .buyerId(buyerInfo.getId())
                    .buyerPhoneNumber(buyerInfo.getPhoneNumber())
                    .buyerName(buyerInfo.getUsername())
                    .sellerId(productInfo.getSellerId())
                    .status(OrderLineStatus.PROCESSING)
                    .statusHistory(List.of(initialHistory))
                    .build();
            orderLineEntities.add(orderLine);
        }

        OrderEntity orderEntity = OrderEntity.builder()
                .buyerId(request.getBuyerId())
                .totalAmount(totalAmount)
                .shippingFee(BigDecimal.ZERO)
                .deliveryAddress(request.getDeliveryAddress())
                .subTotal(totalAmount)
                .build();

        orderEntity = orderRepository.save(orderEntity);
        for (OrderLineEntity orderLineEntity : orderLineEntities) {
            orderLineEntity.setOrderId(orderEntity.getId());
        }
        orderLineRepository.saveAll(orderLineEntities);
        //Update cart
        //TODO deduct quantity
        Set<CartLine> updatedCartLine = cartEntity.getCartLines().stream()
                .filter((cartLine -> !productIds.contains(cartLine.getProductId())))
                .collect(Collectors.toSet());
        cartEntity.setCartLines(updatedCartLine);
        cartRepository.save(cartEntity);


        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(orderEntity, orderResponse);
        ReserveStockRequest reserveStockRequest = new ReserveStockRequest();
        reserveStockRequest.setCartLines(request.getCartLines());
        reserveStockRequest.setOrderId(orderEntity.getId());
        kafkaTemplate.send("order-created", objectMapper.writeValueAsString(reserveStockRequest));
        return orderResponse;
    }

    @Override
    public Page<OrderResponse> filterOrder(FilterOrderRequest request) {
        Page<OrderEntity> orderEntityPage = orderRepository.filterOrders(request);
        List<OrderResponse> orderResponses = orderMapper.toModelList(orderEntityPage.getContent());
        return new PageImpl<>(orderResponses, orderEntityPage.getPageable(), orderEntityPage.getTotalElements());
    }
}
