package com.snapcart.order_service.dto.request;

import com.snapcart.order_service.dto.response.CartLine;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckOutRequest {
    private String buyerId;
    private List<CartLine> cartLines;
    private String deliveryAddress;
}
