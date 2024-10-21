package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.Size;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class SizeRepo extends Repositories<Size, Long> {

    @Override
    protected Class<Size> getEntityClass() {
        return Size.class;
    }

}

