package com.snapcart.order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutFailResponse {
    private String orderId;
    private String reason;
    private String paymentMethod;
    private String paymentProvider;
    private List<CartLine> cartLines;
}
