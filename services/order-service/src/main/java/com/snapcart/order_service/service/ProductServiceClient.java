package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.request.ReserveStockRequest;
import com.snapcart.order_service.dto.response.ProductInfo;
import com.snapcart.order_service.dto.response.ReserveStockResponse;
import com.snapcart.order_service.dto.response.SnapCartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${url.product-service}")
public interface ProductServiceClient {
    @GetMapping("/products/{id}")
    SnapCartResponse<ProductInfo> getProductInfo(@PathVariable("id") String productId);

    @GetMapping("/products/by-ids")
    SnapCartResponse<List<ProductInfo>> getProductInfoList(@RequestParam("ids") List<String> productIds);

}
