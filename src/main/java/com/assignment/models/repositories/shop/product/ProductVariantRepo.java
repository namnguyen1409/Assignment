package com.assignment.models.repositories.shop.product;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ProductVariant;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ProductVariantRepo extends Repositories<ProductVariant, Long> {

    @Override
    protected Class<ProductVariant> getEntityClass() {
        return ProductVariant.class;
    }


    // lấy biến thể kèm product
    public ProductVariant findByIdWithProduct(Long id) {
        var customQuery = new QueryBuilder<>(ProductVariant.class);
        customQuery.from("pv")
        .fetch("product")
        .where("pv.id", id);
        return getSingleResult(customQuery);
    }

    // lấy tất ca các biến thể của 1 sản phẩm
    public List<ProductVariant> findByProductId(Long productId) {
        var customQuery = new QueryBuilder<>(ProductVariant.class);
        customQuery.from()
        .fetch("size")
        .fetch("style")
        .where("product.id", productId);
        return getResultList(customQuery);
    }

}

