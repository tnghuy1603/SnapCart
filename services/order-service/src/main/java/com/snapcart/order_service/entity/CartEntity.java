package com.snapcart.order_service.entity;

import com.snapcart.order_service.dto.response.CartLine;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("cart")
public class CartEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String buyerId;
    private String totalAmount;
    private BigDecimal shippingFee;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private Set<CartLine> cartLines;
}
