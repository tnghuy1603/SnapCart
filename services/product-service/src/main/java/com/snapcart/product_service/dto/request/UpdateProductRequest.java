package com.snapcart.product_service.dto.request;

import com.snapcart.product_service.entity.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UpdateProductRequest {
    private String name;
    private String description;
    private ProductCategory category;
    private List<String> specifications;
    private List<String> features;
    private List<String> images;
    private String thumbnail;
    private BigDecimal price;
    private int stock;
}
