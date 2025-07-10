package com.snapcart.order_service.dto.request;

import com.snapcart.order_service.dto.response.CartLine;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReserveStockRequest {
    private String orderId;
    private String paymentMethod;
    private String paymentProvider;
    private List<CartLine> cartLines;
}
