package com.assignment.models.repositories.ewallet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.ewallet.BankEwallet;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class BankEwalletRepo extends Repositories<BankEwallet, Long> {

    @Override
    protected Class<BankEwallet> getEntityClass() {
        return BankEwallet.class;
    }

}

