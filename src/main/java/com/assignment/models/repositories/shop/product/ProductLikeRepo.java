package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ProductLike;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ProductLikeRepo extends Repositories<ProductLike, Long> {

    @Override
    protected Class<ProductLike> getEntityClass() {
        return ProductLike.class;
    }

}
