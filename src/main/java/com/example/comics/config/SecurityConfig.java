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
        http
                // 1. Deshabilitar CSRF (Requisito para que funcionen POST/PUT en APIs)
                .csrf(csrf -> csrf.disable())

                // 2. Autorizar las rutas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/productos/**").permitAll() // Permite todo lo que empiece con /productos
                        .anyRequest().authenticated()                // El resto sí pide login
                );

        return http.build();
    }
}
