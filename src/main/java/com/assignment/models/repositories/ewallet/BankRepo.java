package com.assignment.models.repositories.ewallet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.ewallet.Bank;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class BankRepo extends Repositories<Bank, Long> {

    @Override
    protected Class<Bank> getEntityClass() {
        return Bank.class;
    }

}

