package com.snapcart.order_service.dto.request;

import com.snapcart.order_service.constant.OrderLineStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterOrderRequest {
    private String orderId;
    private String buyerId;
    private int limit = 8;
    private int offset = 0;
}
