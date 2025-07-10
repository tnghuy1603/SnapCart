package com.snapcart.product_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapcart.product_service.dto.mapper.ProductMapper;
import com.snapcart.product_service.dto.request.AddProductRequest;
import com.snapcart.product_service.dto.request.FilterProductRequest;
import com.snapcart.product_service.dto.request.ReserveStockRequest;
import com.snapcart.product_service.dto.request.UpdateProductRequest;
import com.snapcart.product_service.dto.response.ProductResponse;
import com.snapcart.product_service.dto.response.ReserveStockResponse;
import com.snapcart.product_service.entity.ProductEntity;
import com.snapcart.product_service.repository.ProductRepository;
import com.snapcart.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public ProductResponse createProduct(AddProductRequest request) {
        ProductEntity productEntity = productMapper.toEntity(request);
        productEntity.setSoldCount(0);
        productEntity.setRating(0);
        productEntity = productRepository.save(productEntity);
        return productMapper.toModel(productEntity);
    }

    @Override
    public Page<ProductResponse> filterProducts(FilterProductRequest request, int limit, int offset) {
        Pageable pageRequest = PageRequest.of(offset/ limit, limit);
        Page<ProductEntity> productEntityPage = productRepository.filterProducts(request, pageRequest);
        List<ProductResponse> productResponses = productMapper.toModelList(productEntityPage.getContent());
        return new PageImpl<>(productResponses, pageRequest, productEntityPage.getTotalElements());
    }

    @Override
    public ProductResponse updateProduct(String productId, UpdateProductRequest request) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        BeanUtils.copyProperties(request, productEntity);
        productEntity = productRepository.save(productEntity);
        return productMapper.toModel(productEntity);
    }

    @Override
    public ProductResponse deleteProduct(String productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productEntity.setDeleted(true);
        productEntity = productRepository.save(productEntity);
        return productMapper.toModel(productEntity);
    }

    @Override
    public ProductResponse getProductById(String productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productEntity = productRepository.save(productEntity);
        return productMapper.toModel(productEntity);
    }

    @Override
    public List<ProductResponse> getProductByIds(List<String> productIds) {
        List<ProductEntity> entities = productRepository.findByIdIsIn(productIds);
        return productMapper.toModelList(entities);
    }

    @SneakyThrows
    @Override
    public void reserveStock(ReserveStockRequest request) {
        List<String> productIds = request.getCartLines()
                .stream()
                .map(ReserveStockRequest.CartLine::getProductId)
                .toList();
        List<ProductEntity> entities = productRepository.findByIdIsIn(productIds);
        ReserveStockResponse result;
        ReserveStockResponse.ReserveStockResponseBuilder builder = ReserveStockResponse.builder();
        builder.orderId(request.getOrderId());
        if (productIds.size() != entities.size()) {
            result = builder
                    .message("One or more products do not match")
                    .success(false)
                    .build();
            kafkaTemplate.send("reserve-stock", objectMapper.writeValueAsString(result));
        }
        Map<String, ProductEntity> productMap = entities.stream()
                .collect(Collectors.toMap(ProductEntity::getId, e -> e));

        for (ReserveStockRequest.CartLine cartLine : request.getCartLines()) {
            ProductEntity productEntity = productMap.get(cartLine.getProductId());
            if (productEntity.isDeleted()) {
                result = builder.message("Product is deleted").success(false).build();
                kafkaTemplate.send("reserve-stock", objectMapper.writeValueAsString(result));
            }
            if (productEntity.getStock() > cartLine.getQuantity()) {
                result = builder.message("Quantity exceeds stock").build();
                kafkaTemplate.send("reserve-stock", objectMapper.writeValueAsString(result));
            }
            productEntity.setStock(productEntity.getStock() - cartLine.getQuantity());
        }
        productRepository.saveAll(productMap.values());
         builder.message("Reserved").success(true).build();
         kafkaTemplate.send("reserve-stock", objectMapper.writeValueAsString(builder.build()));
    }

}
