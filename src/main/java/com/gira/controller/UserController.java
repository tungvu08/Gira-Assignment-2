package com.gira.controller;

import com.gira.dto.UserDto;
import com.gira.form.UserCreateForm;
import com.gira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/api/v1/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create (@RequestBody UserCreateForm form){
return  userService.create(form);
    }
}
