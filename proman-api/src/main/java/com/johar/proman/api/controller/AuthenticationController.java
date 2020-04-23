package com.johar.proman.api.controller;


import com.johar.proman.api.model.AuthorizedUserResponse;
import com.johar.proman.service.business.AuthenticationService;
import com.johar.proman.service.entity.UserAuthTokenEntity;
import com.johar.proman.service.entity.UserEntity;
import com.johar.proman.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @RequestMapping(method = RequestMethod.POST, path = "auth/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AuthorizedUserResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException
    {
        //Basic dXNlcm5hbWU6cGFzc3dvcmQ=
        //above is a sample encoded text where the username is "username" and password is "password" seperated by a ":"
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");

        UserAuthTokenEntity userAuthToken = authenticationService.authenticate(decodedArray[0],decodedArray[1]);
        UserEntity user = userAuthToken.getUser();

        AuthorizedUserResponse authorizedUserResponse =  new AuthorizedUserResponse().id(UUID.fromString(user.getUuid()))
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .emailAddress(user.getEmail()).mobilePhone(user.getMobilePhone())
                .lastLoginTime(user.getLastLoginAt());

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", userAuthToken.getAccessToken());
        return new ResponseEntity<AuthorizedUserResponse>(authorizedUserResponse,headers, HttpStatus.OK);

    }

}



