package com.snapcart.product_service.dto.mapper;

import com.snapcart.product_service.dto.request.AddProductRequest;
import com.snapcart.product_service.dto.response.ProductResponse;
import com.snapcart.product_service.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toModel(ProductEntity entity);
    List<ProductResponse> toModelList(List<ProductEntity> entities);
    ProductEntity toEntity(AddProductRequest request);
}
