package com.assignment.models.repositories.shop.order;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.order.OrderReturn;
import com.assignment.models.entities.shop.order.ReturnStatus;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class OrderReturnRepo extends  Repositories<OrderReturn, Long> {

    @Override
    protected Class<OrderReturn> getEntityClass() {
        return OrderReturn.class;
    }

    // đếm số đơn chờ xử lý trả hàng
    public long countOrderReturnPending(long storeId) {
        var customQuery = new QueryBuilder<>(OrderReturn.class);
        customQuery.count("o")
                .where("o.store.id", storeId)
                .and("o.returnStatus", ReturnStatus.PENDING);
        return count(customQuery);
    }

    
}
