package com.assignment.models.repositories.shop.voucher;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.voucher.VoucherType;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class VoucherTypeRepo extends Repositories<VoucherType, Long> {

    @Override
    protected Class<VoucherType> getEntityClass() {
        return VoucherType.class;
    }

}

