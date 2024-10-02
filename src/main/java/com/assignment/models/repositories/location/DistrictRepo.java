package com.assignment.models.repositories.location;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.location.District;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DistrictRepo extends Repositories<District, Long> {

    @Override
    protected Class<District> getEntityClass() {
        return District.class;
    }

}
