package com.example.eventbookingsystem.config;

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
        // TODO: Configure security rules
        // Allow all users to view events and order tickets
        // Restrict event creation, update, and deletion to Event Organizers
        // Allow access to /api/users/register and /api/users/login endpoints
        return http.build();
    }

    // TODO: Add password encoder bean

    // TODO: Add authentication manager bean
}
