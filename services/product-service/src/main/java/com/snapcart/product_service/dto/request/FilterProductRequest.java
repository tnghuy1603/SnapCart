package com.snapcart.product_service.dto.request;

import com.snapcart.product_service.entity.ProductSortBy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FilterProductRequest {
    private String name;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minRating;
    private Boolean inStockOnly = true;
    private String sellerId;
    private Boolean isDeleted = false;
    private ProductSortBy sortBy;
}
