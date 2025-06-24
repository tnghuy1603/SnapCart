package com.snapcart.order_service.dto.response;

import com.snapcart.order_service.constant.OrderLineStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class OrderLineResponse {
    private String id;
    private String orderId;
    private String productName;
    private List<String> images;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;
    private String cancelReason;
    private OrderLineStatus status;
    private List<OrderStatusHistory> statusHistory;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
