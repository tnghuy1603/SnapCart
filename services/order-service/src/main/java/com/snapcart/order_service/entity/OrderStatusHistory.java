package com.snapcart.order_service.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class OrderStatusHistory {
    private OrderStatus status;
    private LocalDateTime updatedAt;
    private String note;
}
