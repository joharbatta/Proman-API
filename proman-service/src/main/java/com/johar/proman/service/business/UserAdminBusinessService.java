package com.johar.proman.service.business;

import com.johar.proman.service.dao.UserDao;
import com.johar.proman.service.entity.UserEntity;
import com.johar.proman.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAdminBusinessService {

    @Autowired
    private UserDao userDao;

    public UserEntity getUser(final String userUuid) throws ResourceNotFoundException {
        UserEntity userEntity=userDao.getUser(userUuid);
        if(userEntity==null)
        {
            throw new ResourceNotFoundException("USR-001","User not found");
        }
        return userEntity;
    }
}
//throw for raise exception
//throws just to let other person know that these method can generate this type of exception
