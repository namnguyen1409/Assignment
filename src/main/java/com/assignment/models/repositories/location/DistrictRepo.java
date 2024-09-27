package com.assignment.models.repositories.location;

import com.assignment.models.entities.location.District;
import com.assignment.models.repositories.Repositories;

public class DistrictRepo extends Repositories<District, Long> {

    @Override
    protected Class<District> getEntityClass() {
        return District.class;
    }

}
