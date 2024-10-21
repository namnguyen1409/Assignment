package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ReportStatus;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ReportStatusRepo extends Repositories<ReportStatus, Long> {

    @Override
    protected Class<ReportStatus> getEntityClass() {
        return ReportStatus.class;
    }

}

