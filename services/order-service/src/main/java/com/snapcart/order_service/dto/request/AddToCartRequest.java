package com.snapcart.order_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private String productId;
    private int quantity;
}
