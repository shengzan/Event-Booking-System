package com.example.eventbookingsystem.config;

import com.example.eventbookingsystem.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceConfig {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceConfig(UserService userService) {
        this.userService = userService;
    }
}
