package com.johar.proman.service.dao;

import com.johar.proman.service.entity.UserAuthTokenEntity;
import com.johar.proman.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.parser.Entity;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity createUser(UserEntity userEntity)
    {
         entityManager.persist(userEntity);
         return userEntity;
    }

    //get user details by uuid
    //another way then creating transaction everytime
    // user di uuid aaugi phr oh userenetity ch dynamic query likhi h oh return krdu result
    //get single result matlab ki aapa nu ptta ki ek toh zyaada result ho nhi skda bcz uuid is unique
    public UserEntity getUser(final String userUuid)
    {
        try{
            return entityManager.createNamedQuery("userByUuid", UserEntity.class)
                    .setParameter("uuid", userUuid)
                    .getSingleResult();
        }
        catch (NoResultException n){
            return null;
        }
    }

    //search by email or username
    public UserEntity getUserByEmail(final String email)
    {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
// databse ch user auth token save horea
    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity){
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }
    public void updateUser(final UserEntity updatedUserEntity){
        entityManager.merge(updatedUserEntity);
    }




}
