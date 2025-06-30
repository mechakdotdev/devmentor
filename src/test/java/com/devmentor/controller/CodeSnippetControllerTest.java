package com.devmentor.controller;

import com.devmentor.dto.request.CodeSnippetRequestDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.service.CodeSnippetService;
import com.devmentor.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CodeSnippetController.class)
class CodeSnippetControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean
    private CodeSnippetService codeSnippetService;
    @MockitoBean private UserService userService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void should_Return200AndSavedSnippet_WhenValidCodeSnippetRequest() throws Exception {
        // Arrange
        CodeSnippetRequestDto dto = CodeSnippetRequestDto.builder()
                .title("StartProgram")
                .language("Java")
                .content("public class StartProgram {}")
                .username("mock-user")
                .build();

        User user = User.builder()
                .id(UUID.randomUUID())
                .username("mock-user")
                .email("user@mock.com")
                .role(User.Role.JUNIOR)
                .build();

        CodeSnippet snippet = CodeSnippet.builder()
                .id(UUID.randomUUID())
                .title(dto.getTitle())
                .language(dto.getLanguage())
                .content(dto.getContent())
                .submittedBy(user)
                .build();

        Mockito.when(userService.getUser("mock-user")).thenReturn(user);
        Mockito.when(codeSnippetService.submit(Mockito.any())).thenReturn(snippet);

        // Act
        // Assert
        mockMvc.perform(post("/api/snippets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("StartProgram"))
                .andExpect(jsonPath("$.language").value("Java"))
                .andExpect(jsonPath("$.submittedBy.email").value("user@mock.com"));
    }

    @Test
    void should_ReturnBackRequest_When_InvalidCodeSnippetRequestSubmitted() throws Exception {
        // Arrange
        CodeSnippetRequestDto dto = CodeSnippetRequestDto.builder()
                .title("")
                .language("")
                .content("")
                .username("")
                .build();

        // Act
        // Assert
        mockMvc.perform(post("/api/snippets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").value("Title is required"))
                .andExpect(jsonPath("$.errors.language").value("Language is required"))
                .andExpect(jsonPath("$.errors.content").value("Content is required"))
                .andExpect(jsonPath("$.errors.username").value("Username is required"));
    }
}