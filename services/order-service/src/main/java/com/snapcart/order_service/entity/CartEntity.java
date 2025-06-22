package com.snapcart.order_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("cart")
public class CartEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String buyerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartLine> cartLines;
}
