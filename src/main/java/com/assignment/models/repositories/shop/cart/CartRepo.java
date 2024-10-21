package com.assignment.models.repositories.shop.cart;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.cart.Cart;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class CartRepo extends Repositories<Cart, Long> {

    @Override
    protected Class<Cart> getEntityClass() {
        return Cart.class;
    }

    // lấy giỏ hàng của người dùng
    public Cart findByUserId(Long userId) {
        var customQuery = new QueryBuilder<>(Cart.class);
        customQuery.from("c")
                .where("c.user.id", userId);
        return getSingleResult(customQuery);
    }



}

