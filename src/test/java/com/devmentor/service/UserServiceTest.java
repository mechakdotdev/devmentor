package com.devmentor.service;

import com.devmentor.entity.User;
import com.devmentor.exception.ResourceNotFoundException;
import com.devmentor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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

    private User mockUser;

    @BeforeEach
    void setup() {
        mockUser = User.builder()
                .id(UUID.randomUUID())
                .username("mock-user")
                .email("user@mock.com")
                .role(User.Role.DEVELOPER)
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
    void should_ReturnUser_When_UserExists() {
        // Arrange
        when(userRepository.findByUsername("mock-user")).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUser("mock-user");

        // Assert
        assertThat(result).isEqualTo(mockUser);
        verify(userRepository).findByUsername("mock-user");
    }

    @Test
    void should_ThrowException_When_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonexistent-user")).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThatThrownBy(() -> userService.getUser("nonexistent-user"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with username: nonexistent-user");

        verify(userRepository).findByUsername("nonexistent-user");
    }

    @Test
    void should_ReturnUserList_When_UsersExist() {
        List<String> usernames = List.of("mock-user");
        List<User> users = List.of(mockUser);

        when(userRepository.findAllByUsernameIn(usernames)).thenReturn(Optional.of(users));

        List<User> result = userService.getUsers(usernames);

        assertThat(result).containsExactly(mockUser);
        verify(userRepository).findAllByUsernameIn(usernames);
    }

    @Test
    void should_ThrowException_When_UsersNotFound() {
        List<String> usernames = List.of("nonexistent-user");

        when(userRepository.findAllByUsernameIn(usernames)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUsers(usernames))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("None of the listed users were found");

        verify(userRepository).findAllByUsernameIn(usernames);
    }
}