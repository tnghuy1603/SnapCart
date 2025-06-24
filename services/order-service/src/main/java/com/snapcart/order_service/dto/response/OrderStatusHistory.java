package com.snapcart.order_service.dto.response;

import com.snapcart.order_service.constant.OrderLineStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class OrderStatusHistory {
    private OrderLineStatus fromStatus;
    private OrderLineStatus toStatus;
    private LocalDateTime timestamp;
}
