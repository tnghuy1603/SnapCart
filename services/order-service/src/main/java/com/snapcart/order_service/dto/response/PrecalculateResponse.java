package com.snapcart.order_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Builder
@Setter
@Getter
public class PrecalculateResponse {
    private BigDecimal subTotal;
    private BigDecimal shippingFee;
}
