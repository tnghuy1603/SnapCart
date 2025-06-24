package com.snapcart.order_service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CartLineResponse {
    private String productId;
    private String name;
    private List<String> images;
    private int quantity;
    private boolean outOfStock;
    private BigDecimal price;
    private BigDecimal subtotal;
}
