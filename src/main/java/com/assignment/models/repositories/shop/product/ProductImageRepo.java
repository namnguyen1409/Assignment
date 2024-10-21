package com.assignment.models.repositories.shop.product;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ProductImage;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ProductImageRepo extends Repositories<ProductImage, Long> {

    @Override
    protected Class<ProductImage> getEntityClass() {
        return ProductImage.class;
    }

    // lấy tất cả ảnh của 1 sản phẩm
    public List<ProductImage> findByProductId(Long productId) {
        var customQuery = new QueryBuilder<>(ProductImage.class);
        customQuery.from().where("product.id", productId);
        return getResultList(customQuery);
    }

}
