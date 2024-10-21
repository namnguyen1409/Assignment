package com.assignment.models.repositories.ewallet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.ewallet.TransactionType;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class TransactionTypeRepo extends Repositories<TransactionType, Long> {

    @Override
    protected Class<TransactionType> getEntityClass() {
        return TransactionType.class;
    }

}

