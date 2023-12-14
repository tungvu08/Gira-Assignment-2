package com.gira.configuration;

import com.gira.configuration.jwt.JwtFilterConfigurer;
import com.gira.configuration.jwt.JwtLoginFilter;
import com.gira.exception.ErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ErrorHandler errorHandler) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(Customizer.withDefaults())
                    .exceptionHandling(customizer -> customizer
                            .authenticationEntryPoint(errorHandler)
                            .accessDeniedHandler(errorHandler))
                    .authorizeHttpRequests(customizer -> customizer
                    .requestMatchers(HttpMethod.POST, "/api/v1/user")
                    .permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/post")
                            .hasAuthority("SCOPE_ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/post")
                            .hasAuthority("SCOPE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/post")
                            .hasAuthority("SCOPE_ADMIN")
                    .anyRequest()
                    .authenticated())
                    .sessionManagement(customizer -> customizer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .oauth2ResourceServer(customizer -> customizer
                            .jwt(Customizer.withDefaults()))
                    .apply ( new JwtFilterConfigurer());
    return http.build();
}

@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
}
}
