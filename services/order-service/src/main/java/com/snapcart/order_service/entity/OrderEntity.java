package com.snapcart.order_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("order")
public class OrderEntity {
    private String id;
    private String buyerId;
    private String sellerId;
    private String paymentId;
    private String deliveryAddress;
    private BigDecimal subTotal;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private List<OrderLine> orderLines;
    private List<OrderStatusHistory> statusHistory;
}
