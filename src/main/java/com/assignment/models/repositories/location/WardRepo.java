package com.assignment.models.repositories.location;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.location.District;
import com.assignment.models.entities.location.Ward;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class WardRepo extends Repositories<Ward, Long> {

    @Override
    protected Class<Ward> getEntityClass() {
        return Ward.class;
    }

    public List<Ward> findAllByDistrict(District district) {
        return findAllBy("district", district);
    }
    
}
