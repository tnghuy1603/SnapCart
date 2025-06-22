package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.response.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient
public interface ProductServiceClient {
    @GetMapping
    ProductInfo getProductInfo(@RequestParam String productId);
}
