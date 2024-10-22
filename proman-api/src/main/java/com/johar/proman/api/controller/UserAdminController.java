package com.johar.proman.api.controller;

import com.johar.proman.api.model.UserDetailsResponse;
import com.johar.proman.api.model.UserStatusType;
import com.johar.proman.service.business.UserAdminBusinessService;
import com.johar.proman.service.entity.UserEntity;
import com.johar.proman.service.exception.ResourceNotFoundException;
import com.johar.proman.service.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserAdminController {

    @Autowired
    private UserAdminBusinessService userAdminBusinessService;

    @RequestMapping(method= RequestMethod.GET,path = "/users/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") final String userUuid) throws ResourceNotFoundException
    {
        final UserEntity userEntity=userAdminBusinessService.getUser(userUuid);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().id(userEntity.getUuid())
                .firstName(userEntity.getFirstName()).lastName(userEntity.getLastName())
                .emailAddress(userEntity.getEmail()).mobileNumber(userEntity.getMobilePhone())
                .status(UserStatusType.valueOf(UserStatus.getEnum(userEntity.getStatus()).name()));

        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);

    }
}
