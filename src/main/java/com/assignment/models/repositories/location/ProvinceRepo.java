package com.assignment.models.repositories.location;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.location.Province;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProvinceRepo extends Repositories<Province, Long> {

    @Override
    protected Class<Province> getEntityClass() {
        return Province.class;
    }
    
}
