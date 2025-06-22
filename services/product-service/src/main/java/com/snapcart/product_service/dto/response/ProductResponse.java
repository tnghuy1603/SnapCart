package com.snapcart.product_service.dto.response;

import com.snapcart.product_service.entity.ProductCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private String id;
    private String name;
    private String sellerId;
    private List<String> specifications;
    private List<String> features;
    private String description;
    private List<String> images;
    private String thumbnail;
    private int stock;
    private ProductCategory category;
    private boolean deleted = false;
    private double rating; // scale of 0 to 5
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
