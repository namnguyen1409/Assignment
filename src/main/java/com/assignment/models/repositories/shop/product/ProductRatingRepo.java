package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ProductRating;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ProductRatingRepo extends Repositories<ProductRating, Long> {

    @Override
    protected Class<ProductRating> getEntityClass() {
        return ProductRating.class;
    }


    // tính trung bình rating của sản phẩm
    public Double getAverageRating(Long productId) {
        QueryBuilder<ProductRating> customQuery = new QueryBuilder<>(ProductRating.class);
        customQuery.avg("rating", "pr")
                .where("pr.product.id", productId);
        return avg(customQuery);
    }

}
