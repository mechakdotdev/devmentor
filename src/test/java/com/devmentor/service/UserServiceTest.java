package com.devmentor.service;

import com.devmentor.entity.User;
import com.devmentor.exception.ResourceNotFoundException;
import com.devmentor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setup() {
        sampleUser = User.builder()
                .id(UUID.randomUUID())
                .username("test-user")
                .email("user@test.com")
                .role(User.Role.JUNIOR)
                .build();
    }

    @Test
    void should_SaveAndReturnUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User result = userService.register(sampleUser);

        assertThat(result).isEqualTo(sampleUser);
        verify(userRepository).save(sampleUser);
    }

    @Test
    void should_ReturnUser_When_UserWithMatchingUsernameExists() {
        when(userRepository.findByUsername("test-user")).thenReturn(Optional.of(sampleUser));

        User result = userService.getByUsername("test-user");

        assertThat(result).isEqualTo(sampleUser);
    }

    @Test
    void should_ThrowException_When_UserWithMatchingUsernameDoesNotExist() {
        when(userRepository.findByUsername("nonexistent-user")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getByUsername("nonexistent-user"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with username: nonexistent-user");
    }
}