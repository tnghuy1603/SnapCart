package com.snapcart.product_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReserveStockResponse {
    private String orderId;
    private String message;
    private boolean success;
}
