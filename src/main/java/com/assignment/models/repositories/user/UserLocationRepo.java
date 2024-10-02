package com.assignment.models.repositories.user;

import org.springframework.stereotype.Repository;

import com.assignment.models.entities.user.UserLocation;
import com.assignment.models.repositories.Repositories;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserLocationRepo extends Repositories<UserLocation, Long> {

    @Override
    protected Class<UserLocation> getEntityClass() {
        return UserLocation.class;
    }
    
}
