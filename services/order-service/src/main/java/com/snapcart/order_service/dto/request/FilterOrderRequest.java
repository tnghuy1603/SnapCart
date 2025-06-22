package com.snapcart.order_service.dto.request;

import com.snapcart.order_service.entity.OrderStatus;

public class FilterOrderRequest {
    private OrderStatus status;
    private String orderId;
    
}
