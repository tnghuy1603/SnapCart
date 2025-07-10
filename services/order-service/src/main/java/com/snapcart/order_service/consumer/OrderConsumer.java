package com.snapcart.order_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapcart.order_service.constant.OrderLineEvent;
import com.snapcart.order_service.dto.request.UpdateOrderLineStatusRequest;
import com.snapcart.order_service.dto.response.CheckoutFailResponse;
import com.snapcart.order_service.dto.response.ReserveStockResponse;
import com.snapcart.order_service.entity.OrderEntity;
import com.snapcart.order_service.repository.OrderRepository;
import com.snapcart.order_service.service.OrderLineService;
import com.snapcart.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "checkout-success", groupId = "order-service")
    public void listen(String message) {
        ReserveStockResponse reserveStockResponse = objectMapper.convertValue(message, ReserveStockResponse.class);
        if (!reserveStockResponse.isSuccess()) {

        }
        OrderEntity orderEntity = orderRepository.findById(reserveStockResponse.getOrderId()).orElse(null);
        //Emit init payment request
    }

    @KafkaListener(topics = "checkout-fail", groupId = "order-service")
    public void handleCheckoutFail(String message) {
        CheckoutFailResponse checkoutFailResponse = objectMapper.convertValue(message, CheckoutFailResponse.class);
        UpdateOrderLineStatusRequest cancelOrderRequest = new UpdateOrderLineStatusRequest();
        cancelOrderRequest.setEvent(OrderLineEvent.FAIL_TO_PLACE);
        orderLineService.sendEvent(checkoutFailResponse.getOrderId(), cancelOrderRequest);
    }
}
