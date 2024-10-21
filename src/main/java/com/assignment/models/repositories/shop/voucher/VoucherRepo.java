package com.assignment.models.repositories.shop.voucher;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.voucher.Voucher;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class VoucherRepo extends Repositories<Voucher, Long> {

    @Override
    protected Class<Voucher> getEntityClass() {
        return Voucher.class;
    }

    public List<Voucher> findAllVoucherByStoreId(Long storeId) {
        var customQuery = new QueryBuilder<>(Voucher.class);
        customQuery.from().where("store.id", storeId);
        return getResultList(customQuery);
    }

    // danh sách mã giảm giá toàn hệ thống
    public List<Voucher> findAllSystemVoucher() {
        var customQuery = new QueryBuilder<>(Voucher.class);
        customQuery.from().whereNull("store");
        return getResultList(customQuery);
    }


}

