package com.snapcart.product_service.controller;

import com.snapcart.product_service.dto.request.AddProductRequest;
import com.snapcart.product_service.dto.request.FilterProductRequest;
import com.snapcart.product_service.dto.request.ReserveStockRequest;
import com.snapcart.product_service.dto.request.UpdateProductRequest;
import com.snapcart.product_service.dto.response.SnapCartResponse;
import com.snapcart.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> filterProducts(FilterProductRequest request,
                                            @RequestParam(value = "limit", required = false, defaultValue = "8") Integer limit,
                                            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return SnapCartResponse.successListResponse(productService.filterProducts(request, limit, offset), null);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody AddProductRequest request) {
        return SnapCartResponse.successResponse(productService.createProduct(request), null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        return SnapCartResponse.successResponse(productService.deleteProduct(id), null);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody UpdateProductRequest request) {
        return SnapCartResponse.successResponse(productService.updateProduct(id, request), null);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable String id) {
        return SnapCartResponse.successResponse(productService.getProductById(id), null);
    }

    @GetMapping("/by-ids")
    public ResponseEntity<?> getProductByIds(@RequestParam("ids") List<String> ids) {
        return SnapCartResponse.successResponse(productService.getProductByIds(ids), null);
    }
    @PostMapping("/reserve-stock")
    public ResponseEntity<?> reserveStock(@RequestBody ReserveStockRequest request) {
        productService.reserveStock(request);
        return SnapCartResponse.successResponse("Success");
    }

}
