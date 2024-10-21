package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ProductShare;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ProductShareRepo extends Repositories<ProductShare, Long> {

    @Override
    protected Class<ProductShare> getEntityClass() {
        return ProductShare.class;
    }

}

