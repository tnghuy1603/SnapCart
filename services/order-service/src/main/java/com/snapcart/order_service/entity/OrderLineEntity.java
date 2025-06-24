package com.snapcart.order_service.entity;

import com.snapcart.order_service.constant.OrderLineStatus;
import com.snapcart.order_service.dto.response.OrderStatusHistory;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("order_line")
public class OrderLineEntity {
    @Id
    private String id;
    private String productId;
    private String buyerId;
    private String buyerName;
    private String buyerPhoneNumber;
    @Indexed
    private String sellerId;
    @Indexed
    private String orderId;
    private String productName;
    private List<String> images;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;
    private String cancelReason;
    private OrderLineStatus status;
    private List<OrderStatusHistory> statusHistory;
    @CreatedDate
    private LocalDateTime updatedAt;
    @LastModifiedDate
    private LocalDateTime createdAt;
}
