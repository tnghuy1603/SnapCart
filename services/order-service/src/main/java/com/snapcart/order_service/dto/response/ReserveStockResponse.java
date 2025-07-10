package com.snapcart.order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveStockResponse {
    private String orderId;
    private boolean success;
    private String message;
}
