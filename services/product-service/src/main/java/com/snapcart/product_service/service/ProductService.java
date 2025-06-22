package com.snapcart.product_service.service;

import com.snapcart.product_service.dto.request.AddProductRequest;
import com.snapcart.product_service.dto.request.FilterProductRequest;
import com.snapcart.product_service.dto.request.UpdateProductRequest;
import com.snapcart.product_service.dto.response.ProductResponse;
import com.snapcart.product_service.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(AddProductRequest request);
    Page<ProductResponse> filterProducts(FilterProductRequest request, int limit, int offset);
    ProductResponse updateProduct(String productId, UpdateProductRequest request);
    ProductResponse deleteProduct(String productId);
    ProductResponse getProductById(String productId);
    List<ProductResponse> getProductByIds(List<String> productIds);
}
