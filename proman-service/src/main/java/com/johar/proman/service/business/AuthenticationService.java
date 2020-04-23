package com.johar.proman.service.business;

import com.johar.proman.service.dao.UserDao;
import com.johar.proman.service.entity.UserAuthTokenEntity;
import com.johar.proman.service.entity.UserEntity;
import com.johar.proman.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity authenticate(final String username, final String password) throws AuthenticationFailedException{

        UserEntity userEntity=userDao.getUserByEmail(username);
        if(userEntity==null)
        {
            throw new AuthenticationFailedException("ATH-001", "User with email not found");
        }

        final String encryptedPassword = cryptographyProvider.encrypt(password, userEntity.getSalt());
        if(encryptedPassword.equals(userEntity.getPassword())){

            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
            userAuthToken.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
            userAuthToken.setLoginAt(now);
            userAuthToken.setExpiresAt(expiresAt);
            userAuthToken.setCreatedBy("api-backend");
            userAuthToken.setCreatedAt(now);

            userDao.createAuthToken(userAuthToken);
            userDao.updateUser(userEntity);
            userEntity.setLastLoginAt(now);

            return userAuthToken;
        }
        else{
            throw new AuthenticationFailedException("ATH-002", "Password Failed");
        }


    }

}