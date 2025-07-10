package com.snapcart.payment_service.dto.request;

import com.snapcart.payment_service.constant.PaymentMethod;
import com.snapcart.payment_service.constant.PaymentProvider;

import java.math.BigDecimal;

public class PaymentInitiateRequest {
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentProvider provider;
    private String userId;
    private String orderId;
}
