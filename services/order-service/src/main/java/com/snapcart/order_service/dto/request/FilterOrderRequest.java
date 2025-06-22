package com.snapcart.order_service.dto.request;

import com.snapcart.order_service.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterOrderRequest {
    private OrderStatus status;
    private String orderId;
    private String buyerName;
    private String productName;
    private int limit = 8;
    private int offset = 0;
}
