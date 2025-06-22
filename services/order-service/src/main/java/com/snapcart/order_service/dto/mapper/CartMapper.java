package com.snapcart.order_service.dto.mapper;

import com.snapcart.order_service.dto.response.CartResponse;
import com.snapcart.order_service.entity.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toModel(CartEntity entity);
}
