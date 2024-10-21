package com.assignment.models.repositories.ewallet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.ewallet.Ewallet;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class EwalletRepo extends Repositories<Ewallet, Long> {

    @Override
    protected Class<Ewallet> getEntityClass() {
        return Ewallet.class;
    }


    // lấy thông tin ví điện tử theo user id
    public Ewallet findByUserIdWithBank(Long userId) {
        QueryBuilder<Ewallet> customQuery = new QueryBuilder<>(Ewallet.class);
        customQuery.from("ew")
                    .fetch("ew.bankEwallets bew")
                    .fetch("bew.bank b")
                    .where("ew.user.id", userId);
        return getSingleResult(customQuery);
    }

}

