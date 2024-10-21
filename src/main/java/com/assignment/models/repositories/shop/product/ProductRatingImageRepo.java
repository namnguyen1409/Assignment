package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ProductRatingImage;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ProductRatingImageRepo extends Repositories<ProductRatingImage, Long> {

    @Override
    protected Class<ProductRatingImage> getEntityClass() {
        return ProductRatingImage.class;
    }

}
