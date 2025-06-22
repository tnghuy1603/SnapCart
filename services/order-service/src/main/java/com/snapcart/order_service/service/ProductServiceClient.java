package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.response.ProductInfo;
import com.snapcart.order_service.dto.response.SnapCartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient
public interface ProductServiceClient {
    @GetMapping
    SnapCartResponse<ProductInfo> getProductInfo(@RequestParam String productId);

    @GetMapping("/by-ids")
    SnapCartResponse<List<ProductInfo>> getProductInfoList(@RequestParam List<String> productIds);
}
