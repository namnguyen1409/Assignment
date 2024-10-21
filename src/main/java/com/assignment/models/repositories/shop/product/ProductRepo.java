package com.assignment.models.repositories.shop.product;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.Product;
import com.assignment.models.entities.shop.product.ProductStatus;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ProductRepo extends Repositories<Product, Long> {

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    // lấy sản phẩm fetch store
    public Product findByIdWithStore(Long id) {
        var customQuery = new QueryBuilder<>(Product.class);
        customQuery.from("p")
                .fetch("p.store")
                .where("p.id", id);
        return getSingleResult(customQuery);
    }

    public Product findByIdWithImagesAndStore(Long id) {
        var customQuery = new QueryBuilder<>(Product.class);
        customQuery.from("p")
                .fetch("p.images")
                .fetch("p.store")
                .where("p.id", id);
        return getSingleResult(customQuery);
    }

    
   
    // đếm sản phẩm của shop gần hết hàng hoặc đã hết hàng
    public long countProductOutOfStock(Long storeId) {
        var customQuery = new QueryBuilder<>(Product.class);
        customQuery
            .countBy("p.id", "p") // Đếm theo id sản phẩm
            .join("p.variants v") // Thực hiện join với bảng variants
            .where("p.store.id", storeId) // Điều kiện lọc theo storeId
            .groupBy("p.id") // Nhóm theo id sản phẩm
            .havingQuery("sum(v.quantity) <= 5"); // Điều kiện having
        return count(customQuery);
    }

    // đếm sản phẩm bị vô hiệu hóa
    public long countProductInactive(Long storeId) {
        var customQuery = new QueryBuilder<>(Product.class);
        customQuery.count("p")
                .where("p.store.id", storeId)
                .and("p.status", ProductStatus.INACTIVE);
        return count(customQuery);
    }

    // list newest product
    public List<Product> listNewestProduct(int page, int limit) {
        var customQuery = new QueryBuilder<>(Product.class);
        customQuery.from("p")
                .where("p.status", ProductStatus.ACTIVE)
                .orderBy("p.createdAt", false);
        return getResultList(page, limit, customQuery);
    }

    public Product findByIdWithImages(Long id) {
        var customQuery = new QueryBuilder<>(Product.class);
        customQuery.from("p")
                .fetch("p.images")
                .where("p.id", id);
        return getSingleResult(customQuery);
    }

    public Product findByIdWithVariants(Long id) {
        var customQuery = new QueryBuilder<>(Product.class);
        customQuery.from("p")
                .fetch("p.variants")
                .where("p.id", id);
        return getSingleResult(customQuery);
    }

    public void increaseViewCount(Long productId) {
        var customQuery = new QueryBuilder<>(Product.class);
        customQuery.update("p")
                .increase("p.viewCount", 1)
                .where("p.id", productId);
        executeUpdate(customQuery);
    }


}
