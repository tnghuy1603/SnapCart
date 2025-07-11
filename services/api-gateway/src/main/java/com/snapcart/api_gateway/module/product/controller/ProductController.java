package com.snapcart.api_gateway.module.product.controller;

import com.snapcart.api_gateway.module.product.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceClient productServiceClient;
    @GetMapping
    public Object filterProducts(@RequestParam Map<String, String> params) {
        return productServiceClient.filterProducts(params);
    }

    @PostMapping
    public Object createProduct(@RequestBody Object request) {
        return productServiceClient.createProduct(request);
    }

    @DeleteMapping("{id}")
    public Object deleteProduct(@PathVariable("id") String id) {
        return productServiceClient.deleteProduct(id);
    }

    @PutMapping("{id}")
    public Object updateProduct(@PathVariable("id") String id, @RequestBody Object request) {
        return productServiceClient.updateProduct(id, request);
    }
}
