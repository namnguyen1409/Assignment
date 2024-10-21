package com.assignment.models.repositories.shop.order;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.order.OrderItems;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class OrderItemsRepo extends Repositories<OrderItems, Long> {

    @Override
    protected Class<OrderItems> getEntityClass() {
        return OrderItems.class;
    }

}
