package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ProductView;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;


@Repository
@Transactional
public class ProductViewRepo extends Repositories<ProductView, Long> {

    @Override
    protected Class<ProductView> getEntityClass() {
        return ProductView.class;
    }

    // kiểm tra xem người dùng đã xem sản phẩm chưa
    public boolean isViewed(Long productId, Long userId) {
        QueryBuilder<ProductView> customQuery = new QueryBuilder<>(ProductView.class);
        customQuery.count("pv")
                .where("pv.product.id", productId)
                .where("pv.user.id", userId);
        return count(customQuery) > 0;
    }




}

