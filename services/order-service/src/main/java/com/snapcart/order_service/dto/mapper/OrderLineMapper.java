package com.snapcart.order_service.dto.mapper;

import com.snapcart.order_service.dto.response.OrderLineResponse;
import com.snapcart.order_service.entity.OrderLineEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderLineMapper {
    public OrderLineResponse toModel(OrderLineEntity entity) {
        if (entity == null) {
            return null;
        }
        OrderLineResponse orderLineResponse = new OrderLineResponse();
        BeanUtils.copyProperties(entity, orderLineResponse);
        return orderLineResponse;
    }

    public List<OrderLineResponse> toModelList(List<OrderLineEntity> entities) {
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
