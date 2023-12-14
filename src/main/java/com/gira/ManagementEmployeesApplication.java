package com.gira;

import com.gira.entity.Role;
import com.gira.entity.User;
import com.gira.repository.RoleRepository;
import com.gira.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class ManagementEmployeesApplication implements CommandLineRunner {
private final UserRepository userRepository;
private final RoleRepository roleRepository;
private final PasswordEncoder passwordEncoder;




    public static void main(String[] args) {
        SpringApplication.run(ManagementEmployeesApplication.class, args);
    }

    @Override
    public void run(String...args){
        var admin = roleRepository.findByType((Role.Type.ADMIN));
        var user     = roleRepository.findByType((Role.Type.USER));
        var account = new User();
        account.setName("Tung Vu");
        account.setUsername("tungvu08");
        account.setEmail("abc@gmail.com");
        account.setPassword(passwordEncoder.encode("123456"));
        account.setRoles(Set.of(admin, user));
        userRepository.save(account);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
