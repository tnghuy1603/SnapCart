package com.snapcart.order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class OrderResponse {
    private String id;
    private String buyerId;
    private String sellerId;
    private String paymentId;
    private String deliveryAddress;
    private BigDecimal subTotal;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
}
