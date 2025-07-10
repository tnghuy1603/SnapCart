package com.snapcart.product_service.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReserveStockRequest {
    private String orderId;
    private String paymentMethod;
    private String paymentProvider;
    private List<CartLine> cartLines;
    @Data
    public static class CartLine {
        private String productId;
        private int quantity;
    }
}
