package com.assignment.models.repositories.ewallet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.ewallet.TransactionHistory;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class TransactionHistoryRepo extends Repositories<TransactionHistory, Long> {

    @Override
    protected Class<TransactionHistory> getEntityClass() {
        return TransactionHistory.class;
    }

}

