package com.assignment.models.repositories.auth;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.auth.Device;
import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.auth.UserDevice;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class UserDeviceRepo extends Repositories<UserDevice, Long> {

    @Override
    protected Class<UserDevice> getEntityClass() {
        return UserDevice.class;
    }

    public UserDevice findByRefreshToken(String refreshToken) {
        return findUniqueBy("refreshToken", refreshToken);
    }

    public UserDevice findByUserAndDevice(User user, Device device) {
        var CustomQuery = new QueryBuilder<>(UserDevice.class);
        CustomQuery.from().where("user", user).and("device", device);
        return getSingleResult(CustomQuery);
    }
    
}
