package com.assignment.models.repositories.user;

import com.assignment.models.entities.user.UserLocation;
import com.assignment.models.repositories.Repositories;

public class UserLocationRepo extends Repositories<UserLocation, Long> {

    @Override
    protected Class<UserLocation> getEntityClass() {
        return UserLocation.class;
    }
    
}
