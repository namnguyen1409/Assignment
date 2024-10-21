package com.assignment.models.repositories.shop.order;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.order.Order;
import com.assignment.models.entities.shop.order.OrderStatus;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class OrderRepo extends Repositories<Order, Long> {

    @Override
    protected Class<Order> getEntityClass() {
        return Order.class;
    }

    // đếm số đơn hàng của shop chưa xác nhận
    public long countOrderPending(long storeId) {
        var customQuery = new QueryBuilder<>(Order.class);
        customQuery.count("o")
                .where("o.store.id", storeId)
                .and("o.status", OrderStatus.PENDING);
        return count(customQuery);
    }

    // đếm số đơn hàng của shop chờ lấy hàng
    public long countOrderPendingDelivery(long storeId) {
        var customQuery = new QueryBuilder<>(Order.class);
        customQuery.count("o")
                .where("o.store.id", storeId)
                .and("o.status", OrderStatus.PENDING_DELIVERY);
        return count(customQuery);
    }

    // đếm số đơn hàng của shop đang giao
    public long countOrderDelivering(long storeId) {
        var customQuery = new QueryBuilder<>(Order.class);
        customQuery.count("o")
                .where("o.store.id", storeId)
                .and("o.status", OrderStatus.DELIVERING);
        return count(customQuery);
    }
    
    // đếm số đơn hàng của shop đã hủy
    public long countOrderCancelled(long storeId) {
        var customQuery = new QueryBuilder<>(Order.class);
        customQuery.count("o")
                .where("o.store.id", storeId)
                .and("o.status", OrderStatus.CANCELLED);
        return count(customQuery);
    }



}
