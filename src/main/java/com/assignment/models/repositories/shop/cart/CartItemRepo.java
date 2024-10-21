package com.assignment.models.repositories.shop.cart;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.cart.CartItem;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class CartItemRepo extends Repositories<CartItem, Long> {

    @Override
    protected Class<CartItem> getEntityClass() {
        return CartItem.class;
    }

    // lấy danh sách sản phẩm trong giỏ hàng của người dùng
    public List<CartItem> findAllByCartId(Long cartId) {
        QueryBuilder<CartItem> customQuery = new QueryBuilder<>(CartItem.class);
        customQuery.from("ci")
                    .fetch("ci.productVariant pv")
                    .fetch("pv.product p")
                    .fetch("p.store s")
                    .where("ci.cart.id", cartId);
        return getResultList(customQuery);
    }

    // đếm số lượng sản phẩm trong giỏ hàng
    public Long countByCartId(Long cartId) {
        QueryBuilder<CartItem> customQuery = new QueryBuilder<>(CartItem.class);
        customQuery.count("ci")
        .where("ci.cart.id", cartId);
        return count(customQuery);
    }

    public CartItem findByCartIdAndVariantId(Long cartId, Long variantId) {
        QueryBuilder<CartItem> customQuery = new QueryBuilder<>(CartItem.class);
        customQuery.from("ci")
                    .where("ci.cart.id", cartId)
                    .and("ci.productVariant.id", variantId);
        return getSingleResult(customQuery);
    }


}

