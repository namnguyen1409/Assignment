package com.assignment.models.repositories.location;

import com.assignment.models.entities.location.Province;
import com.assignment.models.repositories.Repositories;

public class ProvinceRepo extends Repositories<Province, Long> {

    @Override
    protected Class<Province> getEntityClass() {
        return Province.class;
    }
    
}
