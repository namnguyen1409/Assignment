package com.assignment.models.repositories.location;

import com.assignment.models.entities.location.Ward;
import com.assignment.models.repositories.Repositories;

public class WardRepo extends Repositories<Ward, Long> {

    @Override
    protected Class<Ward> getEntityClass() {
        return Ward.class;
    }
    
}
