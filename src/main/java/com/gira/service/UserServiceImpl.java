package com.gira.service;

import com.gira.dto.UserDto;
import com.gira.entity.Role;
import com.gira.entity.User;
import com.gira.form.UserCreateForm;
import com.gira.repository.RoleRepository;
import com.gira.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;



@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto create(UserCreateForm form) {
        User user = modelMapper.map(form, User.class);
        Role role = roleRepository.findByType(Role.Type.USER);
        user.setRoles(Set.of(role));
        User saveUser = userRepository.save(user);

        return modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        var authorities = new ArrayList<GrantedAuthority>();
        var roles = user.getRoles();
        for (Role  role: roles)
              {
            var authority = new SimpleGrantedAuthority(role.getType().toString());
            authorities.add(authority);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}