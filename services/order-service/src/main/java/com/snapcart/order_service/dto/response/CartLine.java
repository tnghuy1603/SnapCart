package com.snapcart.order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartLine {
    private String productId;
    private int quantity;
}
