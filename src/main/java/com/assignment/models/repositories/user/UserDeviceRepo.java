package com.assignment.models.repositories.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.user.Device;
import com.assignment.models.entities.user.User;
import com.assignment.models.entities.user.UserDevice;
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
        return CustomQuery().where("user", user).and("device", device).getSingleResult();
    }
    
}
