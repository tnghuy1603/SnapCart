package com.snapcart.order_service.dto.request;

import com.snapcart.order_service.constant.OrderLineEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderLineStatusRequest {
    private OrderLineEvent event;
    private String cancelReason;
}
