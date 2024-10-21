package com.assignment.models.repositories.auth;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.models.entities.auth.UserLocation;
import com.assignment.models.repositories.QueryBuilder;
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
        var customQuery = new QueryBuilder<>(UserLocation.class);
        customQuery.from("l")
        .fetch(
        "l.province", 
        "l.district", 
        "l.ward", 
        "l.user")
        .where("l.id", id);
        return getSingleResult(customQuery);
    }

    public List<UserLocation> findByUserIdWithFetch(Long userId) {
        var customQuery = new QueryBuilder<>(UserLocation.class);
        customQuery.from("l")
        .fetch(
        "l.province", 
        "l.district", 
        "l.ward", 
        "l.user")
        .where("l.user.id", userId);
        return getResultList(customQuery);
    }



}
