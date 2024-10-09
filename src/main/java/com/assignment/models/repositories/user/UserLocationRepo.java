package com.assignment.models.repositories.user;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.auth.UserLocation;
import com.assignment.models.repositories.Repositories;

@Repository
@Transactional
public class UserLocationRepo extends Repositories<UserLocation, Long> {

    @Override
    protected Class<UserLocation> getEntityClass() {
        return UserLocation.class;
    }

    @Transactional(readOnly = true)
    public List<UserLocation> findByUserId(Long userId) {
        return getCurrentSession().createQuery("from " + getEntityClass().getName() + " where user_id = :userId", getEntityClass())
                .setParameter("userId", userId).list();
    }

    public UserLocation findByIdWithFetch(Long id) {
        return CustomQuery("l").fetch(
                
        "l.province",
                "l.district",
                "l.ward",
                "l.user"
        ).where("l.id", id).getSingleResult();
    }

}
