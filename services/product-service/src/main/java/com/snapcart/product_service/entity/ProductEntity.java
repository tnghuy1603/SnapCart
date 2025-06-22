package com.snapcart.product_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("product")
public class ProductEntity {
    @Id
    private String id;
    private String name;
    @Indexed
    private String sellerId;
    private List<String> specifications;
    private List<String> features;
    private String description;
    private List<String> images;
    private String thumbnail;
    private int stock;
    private ProductCategory category;
    private boolean deleted = false;
    private double rating;// scale of 0 to 5
    private int soldCount;
    private BigDecimal price;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
