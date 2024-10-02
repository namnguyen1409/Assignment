package com.assignment.models.repositories.location;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.location.Ward;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class WardRepo extends Repositories<Ward, Long> {

    @Override
    protected Class<Ward> getEntityClass() {
        return Ward.class;
    }
    
}
