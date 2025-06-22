package com.snapcart.order_service.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CartLine {
    private String productId;
    private int quantity;
}
