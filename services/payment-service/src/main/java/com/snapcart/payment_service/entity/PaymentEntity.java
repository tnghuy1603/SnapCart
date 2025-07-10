package com.snapcart.payment_service.entity;

import com.snapcart.payment_service.constant.PaymentMethod;
import com.snapcart.payment_service.constant.PaymentProvider;
import com.snapcart.payment_service.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("payment")
public class PaymentEntity {
    @Id
    private String id;
    private String txnIdRef;
    private String orderId;
    private String userId;
    private PaymentProvider provider;
    private PaymentStatus status;
    private PaymentMethod method;
    private BigDecimal amount;
    private String currency;
    private Object providerResponse;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
