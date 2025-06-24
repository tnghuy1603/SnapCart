package com.snapcart.order_service.entity;

import com.snapcart.order_service.constant.OrderLineStatus;
import com.snapcart.order_service.dto.response.OrderStatusHistory;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("order")
public class OrderEntity {
    private String id;
    private String buyerId;
    private String paymentId;
    private String deliveryAddress;
    private BigDecimal subTotal;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
}
