package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.Style;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class StyleRepo extends Repositories<Style, Long> {

    @Override
    protected Class<Style> getEntityClass() {
        return Style.class;
    }

}

