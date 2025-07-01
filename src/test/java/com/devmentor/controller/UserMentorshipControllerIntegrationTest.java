package com.devmentor.controller;

import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.repository.CodeSnippetRepository;
import com.devmentor.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserMentorshipControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private UserRepository userRepository;
    @Autowired private CodeSnippetRepository snippetRepository;

    @Test
    void should_Return200_And_GetCodeSnippets() throws Exception {
        User dev = userRepository.save(User.builder()
                .username("mock-developer")
                .email("developer@mock.com")
                .role(User.Role.DEVELOPER)
                .build());

        snippetRepository.saveAll(List.of(
                CodeSnippet.builder()
                        .title("A")
                        .language("Java")
                        .content("public class A {}")
                        .submittedBy(dev)
                        .build(),
                CodeSnippet.builder()
                        .title("B")
                        .language("JavaScript")
                        .content("function b() {}")
                        .submittedBy(dev)
                        .build()
        ));

        mockMvc.perform(get("/api/users/mock-developer/submissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].submittedBy.username").value("mock-developer"))
                .andExpect(jsonPath("$[1].submittedBy.username").value("mock-developer"));
    }

    @Test
    void should_Return404_And_FailToGetSubmissions_When_UserNotFound() throws Exception {
        mockMvc.perform(get("/api/users/unknownuser/submissions"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found with username: unknownuser"));
    }
}