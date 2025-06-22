package com.snapcart.order_service.service.impl;

import com.snapcart.order_service.dto.mapper.OrderMapper;
import com.snapcart.order_service.dto.request.CheckOutRequest;
import com.snapcart.order_service.dto.request.FilterOrderRequest;
import com.snapcart.order_service.dto.response.OrderResponse;
import com.snapcart.order_service.dto.response.ProductInfo;
import com.snapcart.order_service.entity.CartEntity;
import com.snapcart.order_service.entity.CartLine;
import com.snapcart.order_service.entity.OrderEntity;
import com.snapcart.order_service.entity.OrderLine;
import com.snapcart.order_service.repository.CartRepository;
import com.snapcart.order_service.repository.OrderRepository;
import com.snapcart.order_service.service.OrderService;
import com.snapcart.order_service.service.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final ProductServiceClient productServiceClient;
    @Override
    public OrderResponse createOrder(CheckOutRequest request) {
        CartEntity cartEntity = cartRepository.findByBuyerId(request.getBuyerId());
        if (cartEntity == null) {
            throw new RuntimeException("Not found any cart for user" + request.getBuyerId());
        }
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<String> productIds = cartEntity.getCartLines()
                .stream()
                .map(CartLine::getProductId)
                .toList();
        List<ProductInfo> productInfoList = productServiceClient.getProductInfoList(productIds).getData();
        Map<String, ProductInfo> productInfoMap = productInfoList.stream()
                .collect(Collectors.toMap(ProductInfo::getId, p -> p));

        List<OrderLine> orderLineList = new ArrayList<>();
        for (CartLine cartLine : cartEntity.getCartLines()) {
            ProductInfo productInfo = productInfoMap.get(cartLine.getProductId());
            OrderLine orderLine = OrderLine.builder()
                    .productId(cartLine.getProductId())
                    .productName(productInfo.getName())
                    .quantity(cartLine.getQuantity())
                    .unitPrice(productInfo.getPrice())
                    .subTotal(productInfo.getPrice().multiply(BigDecimal.valueOf(cartLine.getQuantity())))
                    .build();
            orderLineList.add(orderLine);
        }
        OrderEntity orderEntity = OrderEntity.builder()
                .buyerId(request.getBuyerId())
                .orderLines(orderLineList)
                .totalAmount(totalAmount)
                .shippingFee(BigDecimal.ZERO)
                .deliveryAddress(request.getDeliveryAddress())
                .subTotal(totalAmount)
                .build();
        orderEntity = orderRepository.save(orderEntity);
        return orderMapper.toModel(orderEntity);
    }

    @Override
    public List<OrderResponse> filterOrder(FilterOrderRequest request) {
        return List.of();
    }


}
