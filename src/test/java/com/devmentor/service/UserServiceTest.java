package com.devmentor.service;

import com.devmentor.entity.User;
import com.devmentor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setup() {
        mockUser = User.builder()
                .id(UUID.randomUUID())
                .username("mock-user")
                .email("user@mock.com")
                .role(User.Role.JUNIOR)
                .build();
    }

    @Test
    void should_SaveAndReturnUser() {
        // Arrange
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        // Act
        User result = userService.register(mockUser);

        // Assert
        assertThat(result).isEqualTo(mockUser);
        verify(userRepository).save(mockUser);
    }

    @Test
    void should_ReturnUser_When_UserWithMatchingUsernameExists() {
        // Arrange
        when(userRepository.findByUsername("mock-user")).thenReturn(mockUser);

        // Act
        User result = userService.getUser("mock-user");

        // Assert
        assertThat(result).isEqualTo(mockUser);
    }

//    @Test
//    void should_ThrowException_When_UserWithMatchingUsernameDoesNotExist() {
//        // Arrange
//        when(userRepository.findByUsername("nonexistent-user")).thenReturn(null);
//
//        // Act
//        // Assert
//        assertThatThrownBy(() -> userService.getUser("nonexistent-user"))
//                .isInstanceOf(ResourceNotFoundException.class)
//                .hasMessage("User not found with username: nonexistent-user");
//    }
}