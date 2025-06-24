package com.snapcart.order_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
public class CartResponse {
    private String id;
    private String buyerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartLineResponse> cartLines;
}
