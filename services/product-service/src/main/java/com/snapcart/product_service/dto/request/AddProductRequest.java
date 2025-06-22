package com.snapcart.product_service.dto.request;

import com.snapcart.product_service.entity.ProductCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AddProductRequest {
    private String name;
    private String sellerId;
    private List<String> specifications;
    private List<String> features;
    private String description;
    private List<String> images;
    private String thumbnailImage;
    private int stock;
    private BigDecimal price;
    private ProductCategory category;
}
