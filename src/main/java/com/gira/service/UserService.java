package com.gira.service;

import com.gira.dto.UserDto;
import com.gira.form.UserCreateForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto create(UserCreateForm form);
}
