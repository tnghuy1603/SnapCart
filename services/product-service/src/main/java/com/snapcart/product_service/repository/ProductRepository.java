package com.snapcart.product_service.repository;

import com.snapcart.product_service.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<ProductEntity, String>, ProductRepositoryCustom {
    List<ProductEntity> findByIdIsIn(List<String> ids);
}
