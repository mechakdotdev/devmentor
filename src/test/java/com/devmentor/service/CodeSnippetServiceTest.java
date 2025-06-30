package com.devmentor.service;

import com.devmentor.dto.response.CodeSnippetResponseDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.repository.CodeSnippetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodeSnippetServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private CodeSnippetRepository codeSnippetRepository;

    @InjectMocks
    private CodeSnippetService codeSnippetService;

    private CodeSnippet mockSnippet;
    private User mockUser;

    @BeforeEach
    void setup() {
        mockUser = User.builder()
                .id(UUID.randomUUID())
                .username("mock-user")
                .email("user@mock.com")
                .role(User.Role.JUNIOR)
                .build();

        mockSnippet = CodeSnippet.builder()
                .id(UUID.randomUUID())
                .title("Hello World")
                .language("Java")
                .content("System.out.println(\"Hello World!\");")
                .submittedBy(mockUser)
                .build();
    }

    @Test
    void should_SaveAndReturnSnippet_When_ValidSubmission() {
        // Arrange
        when(codeSnippetRepository.save(mockSnippet)).thenReturn(mockSnippet);

        // Act
        CodeSnippet result = codeSnippetService.submit(mockSnippet);

        // Assert
        assertThat(result).isEqualTo(mockSnippet);
        verify(codeSnippetRepository).save(mockSnippet);
    }

    @Test
    void should_ReturnAllSnippetsMatchingGivenUsername() {
        // Arrange
        when(userService.getUser("mock-user")).thenReturn(mockUser);
        when(codeSnippetRepository.findBySubmittedBy(mockUser)).thenReturn(List.of(mockSnippet));

        // Act
        List<CodeSnippetResponseDto> result = codeSnippetService.getSnippetsByUsername("mock-user");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo(mockSnippet.getTitle());
        assertThat(result.getFirst().getLanguage()).isEqualTo(mockSnippet.getLanguage());
        assertThat(result.getFirst().getContent()).isEqualTo(mockSnippet.getContent());
        assertThat(result.getFirst().getSubmittedBy()).isEqualTo(mockSnippet.getSubmittedBy().getUsername());
    }
}