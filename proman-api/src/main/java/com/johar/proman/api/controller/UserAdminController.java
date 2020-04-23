package com.johar.proman.api.controller;

import com.johar.proman.api.model.CreateUserRequest;
import com.johar.proman.api.model.CreateUserResponse;
import com.johar.proman.api.model.UserDetailsResponse;
import com.johar.proman.api.model.UserStatusType;
import com.johar.proman.service.business.UserAdminBusinessService;
import com.johar.proman.service.entity.UserEntity;
import com.johar.proman.service.exception.ResourceNotFoundException;
import com.johar.proman.service.exception.UnauthorizedException;
import com.johar.proman.service.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserAdminController {

    @Autowired
    private UserAdminBusinessService userAdminBusinessService;

    @RequestMapping(method= RequestMethod.GET,path = "/users/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") final String userUuid, @RequestHeader("authorization") final String authorization) throws ResourceNotFoundException, UnauthorizedException
    {
        final UserEntity userEntity=userAdminBusinessService.getUser(userUuid,authorization);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().id(userEntity.getUuid())
                .firstName(userEntity.getFirstName()).lastName(userEntity.getLastName())
                .emailAddress(userEntity.getEmail()).mobileNumber(userEntity.getMobilePhone())
                .status(UserStatusType.valueOf(UserStatus.getEnum(userEntity.getStatus()).name()));

        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }
    //create user by admin
    //didnt set password here
    @RequestMapping(method = RequestMethod.POST, path = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateUserResponse> createUser(final CreateUserRequest userRequest){
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmailAddress());
        userEntity.setMobilePhone(userRequest.getMobileNumber());
        userEntity.setStatus(UserStatus.ACTIVE.getCode());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setCreatedBy("api-backend");

        final UserEntity createdUser = userAdminBusinessService.createUser(userEntity);

        final CreateUserResponse userResponse = new CreateUserResponse().id(createdUser.getUuid()).status(UserStatusType.ACTIVE);

        return new ResponseEntity<CreateUserResponse>(userResponse, HttpStatus.CREATED);

    }

}
