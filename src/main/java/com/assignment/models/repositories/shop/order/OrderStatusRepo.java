package com.assignment.models.repositories.shop.order;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.order.OrderStatus;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class OrderStatusRepo extends Repositories<OrderStatus, Long> {

    @Override
    protected Class<OrderStatus> getEntityClass() {
        return OrderStatus.class;
    }

}
