package com.snapcart.order_service.repository;

import com.snapcart.order_service.dto.request.FilterOrderLineRequest;
import com.snapcart.order_service.entity.OrderLineEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class OrderLineRepositoryCustomImpl implements OrderLineRepositoryCustom {
    private final MongoTemplate mongoTemplate;
    @Override
    public List<OrderLineEntity> filterOrderLine(FilterOrderLineRequest request, Pageable pageable, Sort sort) {
        Query query = buildFilterQuery(request);
        if (pageable != null) {
            query.with(pageable);
        }
        if (sort != null) {
            query.with(sort);
        }
        return mongoTemplate.find(query, OrderLineEntity.class);
    }

    @Override
    public long countOrderLine(FilterOrderLineRequest request) {
        Query query = buildFilterQuery(request);
        return mongoTemplate.count(query, OrderLineEntity.class);
    }

    private Query buildFilterQuery(FilterOrderLineRequest request) {
        List<Criteria> criteriaList = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getOrderId())) {
            criteriaList.add(Criteria.where("orderId").is(request.getOrderId()));
        }
        if (StringUtils.isNotBlank(request.getSellerId())) {
            criteriaList.add(Criteria.where("sellerId").is(request.getSellerId()));
        }
        if (request.getStatus() != null) {
            criteriaList.add(Criteria.where("status").is(request.getStatus()));
        }
        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }
        return query;
    }
}
