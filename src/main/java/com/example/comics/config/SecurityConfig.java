package com.example.comics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Deshabilitar CSRF (Requisito para que funcionen POST/PUT en APIs)
                .csrf(csrf -> csrf.disable())
                // 2. Autorizar a cualquiera de las rutas sin necesidad de login
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }
}
