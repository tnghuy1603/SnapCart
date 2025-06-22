package com.snapcart.order_service.dto.response;

import com.snapcart.order_service.entity.CartLine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class CartResponse {
    private String id;
    private String userId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartLine> cartLines;
}
