package com.snapcart.api_gateway.module.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "product-service", url = "${api.product-service-host}/products")
public interface ProductServiceClient {
    @GetMapping
    ResponseEntity<?> filterProducts(@RequestParam Map<String, String> params);

    @PostMapping
    ResponseEntity<?> createProduct(@RequestBody Object request);

    @DeleteMapping("{id}")
    ResponseEntity<?> deleteProduct(@PathVariable("id") String id);

    @PutMapping("{id}")
    ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Object request);
}
