package com.assignment.models.repositories.shop.product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.product.ProductReport;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class ProductReportRepo extends Repositories<ProductReport, Long> {

    @Override
    protected Class<ProductReport> getEntityClass() {
        return ProductReport.class;
    }

}

