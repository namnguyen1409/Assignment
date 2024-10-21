package com.assignment.models.repositories.shop.store;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.shop.store.Store;
import com.assignment.models.repositories.Repositories;


@Repository
@Transactional
public class StoreRepo extends Repositories<Store, Long> {

    @Override
    protected Class<Store> getEntityClass() {
        return Store.class;
    }

    
    
}
