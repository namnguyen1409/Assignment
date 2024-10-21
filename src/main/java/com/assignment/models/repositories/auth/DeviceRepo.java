package com.assignment.models.repositories.auth;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.auth.Device;
import com.assignment.models.repositories.Repositories;


@Repository
@Transactional
public class DeviceRepo extends Repositories<Device, Long> {

    @Override
    protected Class<Device> getEntityClass() {
        return Device.class;
    }

    public Device findByRegistrationId(String registrationId) {
        return findUniqueBy("registrationId", registrationId);
    }
    
}
