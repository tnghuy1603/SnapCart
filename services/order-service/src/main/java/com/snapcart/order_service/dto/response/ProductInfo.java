package com.snapcart.order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class ProductInfo {
    private String id;
    private String name;
    private String sellerId;
    private List<String> specifications;
    private List<String> features;
    private String description;
    private List<String> images;
    private String thumbnail;
    private int stock;
    private String category;
    private boolean deleted = false;
    private double rating; // scale of 0 to 5
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
