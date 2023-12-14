package com.gira.configuration.jwt;

import com.gira.configuration.SecurityConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtFilterConfigurer extends AbstractHttpConfigurer<JwtFilterConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http){
        var context = http.getSharedObject(ApplicationContext.class);
        var authenticationManager = http.getSharedObject(AuthenticationManager.class);
        var jwtEncoder = context.getBean(JwtEncoder.class);
        var jwtLoginFilter = new JwtLoginFilter(authenticationManager, jwtEncoder);
        http.addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
