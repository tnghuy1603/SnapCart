package com.snapcart.order_service.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderLine {
    private String productId;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal subTotal;
}
