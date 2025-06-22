package com.snapcart.product_service.repository;

import com.snapcart.product_service.dto.request.FilterProductRequest;
import com.snapcart.product_service.entity.ProductEntity;
import com.snapcart.product_service.entity.ProductSortBy;
import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final MongoTemplate mongoTemplate;
    @Override
    public Page<ProductEntity> filterProducts(FilterProductRequest request,@NonNull Pageable pageable) {
        List<Criteria> criteriaList = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getSellerId())) {
            criteriaList.add(Criteria.where("sellerId").is(request.getSellerId()));
        }

        if (StringUtils.isNotBlank(request.getName())) {
            criteriaList.add(Criteria.where("name").regex(request.getName(), "i"));
        }

        if (StringUtils.isNotBlank(request.getCategory())) {
            if (!request.getCategory().equals("ALL")) {
                criteriaList.add(Criteria.where("category").is(request.getCategory()));
            }
        }

        if (request.getMinPrice() != null) {
            criteriaList.add(Criteria.where("price").gte(request.getMinPrice()));
        }

        if (request.getMaxPrice() != null) {
            criteriaList.add(Criteria.where("price").lte(request.getMaxPrice()));
        }

        if (request.getMinRating() != null) {
            criteriaList.add(Criteria.where("rating").gte(request.getMinRating()));
        }

        if (Boolean.TRUE.equals(request.getInStockOnly())) {
            criteriaList.add(Criteria.where("stock").gt(0));
        }

        if (request.getIsDeleted() != null) {
            criteriaList.add(Criteria.where("deleted").is(request.getIsDeleted()));
        }
        Query query = new Query();

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        query.with(pageable);
        if (request.getSortBy() != null) {
            query.with(pageable.getSort());
        }
        List<ProductEntity> entities = mongoTemplate.find(query, ProductEntity.class);
        long count = mongoTemplate.count(query, ProductEntity.class);
        return new PageImpl<>(entities, pageable, count);

    }
    private Sort getSortOption(ProductSortBy sortBy) {
        return switch (sortBy) {
            case PRICE_LOW_TO_HIGH ->
                    Sort.by(Sort.Direction.ASC, "price").and(Sort.by(Sort.Direction.DESC, "updatedAt"));
            case PRICE_HIGH_TO_LOW ->
                    Sort.by(Sort.Direction.DESC, "price").and(Sort.by(Sort.Direction.DESC, "updatedAt"));
            case NEWEST -> Sort.by(Sort.Direction.DESC, "updatedAt");
            case RATING_HIGH_TO_LOW ->
                    Sort.by(Sort.Direction.DESC, "rating").and(Sort.by(Sort.Direction.DESC, "updatedAt"));
            case BEST_SELLING ->
                    Sort.by(Sort.Direction.DESC, "soldCount").and(Sort.by(Sort.Direction.DESC, "updatedAt"));
        };
    }
}
