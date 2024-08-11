package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.model.User;
import com.example.eventbookingsystem.security.JwtTokenProvider;
import com.example.eventbookingsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserDetailsService userDetailsService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
    }

    @Test
    public void testLoginUser() throws Exception {
        when(jwtTokenProvider.createToken(anyString())).thenReturn("testtoken");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\", \"password\":\"testpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer testtoken"));
    }
}
