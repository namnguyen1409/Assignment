package com.assignment.models.repositories.location;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.location.District;
import com.assignment.models.entities.location.Province;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class DistrictRepo extends Repositories<District, Long> {

    @Override
    protected Class<District> getEntityClass() {
        return District.class;
    }

    public List<District> findAllByProvince(Province province) {
        return findAllBy("province", province);
    }

}
