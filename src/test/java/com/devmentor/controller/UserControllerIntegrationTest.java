package com.devmentor.controller;

import com.devmentor.dto.request.UserRegistrationDto;
import com.devmentor.entity.User;
import com.devmentor.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private UserService userService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void should_Return200AndSaveUser_When_ValidUserRegistrationRequest() throws Exception {
        // Arrange
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .username("mock-user")
                .email("user@mock.com")
                .role("DEVELOPER")
                .build();

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .role(User.Role.valueOf(dto.getRole()))
                .build();

        Mockito.when(userService.register(Mockito.any())).thenReturn(savedUser);

        // Act
        // Assert
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mock-user"))
                .andExpect(jsonPath("$.email").value("user@mock.com"))
                .andExpect(jsonPath("$.role").value("DEVELOPER"));
    }

    @Test
    void should_FailValidation_When_InvalidUserRegistrationRequestSubmitted() throws Exception {
        // Arrange
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .username("")
                .email("bademail")
                .role("")
                .build();

        // Act
        // Assert
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.username").value("Username is required"))
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"))
                .andExpect(jsonPath("$.errors.role").value("Role is required"));
    }
}