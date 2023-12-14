package com.gira.repository;

import com.gira.dto.UserDto;
import com.gira.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
