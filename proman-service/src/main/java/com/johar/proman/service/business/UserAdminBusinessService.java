package com.johar.proman.service.business;

import com.johar.proman.service.dao.UserDao;
import com.johar.proman.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAdminBusinessService {

    @Autowired
    private UserDao userDao;

    public UserEntity getUser(final String userUuid){
        return userDao.getUser(userUuid);
    }
}
