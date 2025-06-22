package com.snapcart.product_service.repository;

import com.snapcart.product_service.dto.request.FilterProductRequest;
import com.snapcart.product_service.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductEntity> filterProducts(FilterProductRequest request, Pageable pageable);
}
