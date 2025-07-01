package com.devmentor.controller;

import com.devmentor.dto.request.ReviewCreateRequestDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.entity.Review;
import com.devmentor.repository.CodeSnippetRepository;
import com.devmentor.repository.ReviewRepository;
import com.devmentor.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReviewControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;
    @Autowired private CodeSnippetRepository snippetRepository;
    @Autowired private ReviewRepository reviewRepository;

    @Test
    void should_Return200_And_CreateReview() throws Exception {
        // Arrange
        User mockDeveloper = userRepository.save(User.builder()
                .username("mock-developer")
                .email("developer@mock.com")
                .role(User.Role.DEVELOPER)
                .build());

        userRepository.save(User.builder()
                .username("mock-reviewer")
                .email("reviewer@mock.com")
                .role(User.Role.REVIEWER)
                .build());

        CodeSnippet snippet = snippetRepository.save(CodeSnippet.builder()
                .title("Test")
                .language("Java")
                .content("System.out.println(\"Hello, World!\");")
                .submittedBy(mockDeveloper)
                .build());

        ReviewCreateRequestDto dto = ReviewCreateRequestDto.builder()
                .codeSnippetId(snippet.getId())
                .reviewers(List.of("mock-reviewer"))
                .feedback("Good job")
                .build();

        // Act
        // Assert
        mockMvc.perform(post("/api/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codeSnippetId").value(snippet.getId().toString()))
                .andExpect(jsonPath("$.reviewers[0]").value("mock-reviewer"))
                .andExpect(jsonPath("$.feedback").value("Good job"));
    }

    @Test
    void should_Return404_And_FailToCreateReview_When_SnippetMissingFromReviewCreateRequest() throws Exception {
        // Arrange
        userRepository.save(User.builder()
                .username("mock-reviewer")
                .email("mock@reviewer.com")
                .role(User.Role.REVIEWER)
                .build());

        ReviewCreateRequestDto dto = ReviewCreateRequestDto.builder()
                .codeSnippetId(UUID.randomUUID())
                .reviewers(List.of("mock-reviewer"))
                .feedback("Feedback")
                .build();

        // Act
        // Assert
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Code snippet not found"));
    }

    @Test
    void should_Return200_And_ReturnReview_When_ReviewFound() throws Exception {
        // Arrange
        User dev = userRepository.save(User.builder().username("mock-developer").email("mock@developer.com").role(User.Role.DEVELOPER).build());
        User reviewer = userRepository.save(User.builder().username("mock-reviewer").email("mock@reviewer.com").role(User.Role.REVIEWER).build());

        CodeSnippet snippet = snippetRepository.save(CodeSnippet.builder()
                .title("Test")
                .language("Java")
                .content("test")
                .submittedBy(dev)
                .build());

        Review review = reviewRepository.save(Review.builder()
                .codeSnippet(snippet)
                .reviewers(List.of(reviewer))
                .feedback("Nice")
                .createdAt(LocalDateTime.now())
                .build());

        // Act
        // Assert
        mockMvc.perform(get("/api/reviews/snippet/" + snippet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(review.getId().toString()))
                .andExpect(jsonPath("$.feedback").value("Nice"));
    }

    @Test
    void should_Return404_And_FailToGetReview_When_ReviewNotFound() throws Exception {
        // Arrange
        UUID snippetId = UUID.randomUUID();

        // Act
        // Assert
        mockMvc.perform(get("/api/reviews/snippet/" + snippetId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Review not found for snippet ID: " + snippetId));
    }

    @Test
    void should_Return200_And_ReturnReviews_When_ReviewsWithMatchingReviewerFound() throws Exception {
        // Arrange
        User mockReviewer = userRepository.save(User.builder().username("mock-reviewer").email("mock@reviewer.com").role(User.Role.REVIEWER).build());
        User mockDeveloper = userRepository.save(User.builder().username("mock-developer").email("mock@developer.com").role(User.Role.DEVELOPER).build());

        CodeSnippet snippet = snippetRepository.save(CodeSnippet.builder()
                .title("Mock Snippet")
                .language("Python")
                .content("System.out.println(\"Hello, World!\");")
                .submittedBy(mockDeveloper)
                .build());

        reviewRepository.save(Review.builder()
                .codeSnippet(snippet)
                .reviewers(List.of(mockReviewer))
                .feedback("ok")
                .createdAt(LocalDateTime.now())
                .build());

        // Act
        // Assert
        mockMvc.perform(get("/api/reviews/reviewer")
                        .param("username", "mock-reviewer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].feedback").value("ok"));
    }

    @Test
    void should_Return404_And_FailToGetReview_When_NoReviewsFoundWithMatchingReviewer() throws Exception {
        // Arrange

        // Act
        // Assert
        mockMvc.perform(get("/api/reviews/reviewer")
                        .param("username", "nonexistent-reviewer"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with username: nonexistent-reviewer"));
    }
}
