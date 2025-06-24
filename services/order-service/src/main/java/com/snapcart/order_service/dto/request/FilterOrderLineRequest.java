package com.snapcart.order_service.dto.request;

import com.snapcart.order_service.constant.OrderLineStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterOrderLineRequest {
    private String orderId;
    private OrderLineStatus status;
    private String sellerId;

}
