package com.devmentor.service;

import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.exception.ResourceNotFoundException;
import com.devmentor.repository.CodeSnippetRepository;
import com.devmentor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CodeSnippetRepository codeSnippetRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    public List<User> getUsers(List<String> usernames) {
        return userRepository.findAllByUsernameIn(usernames)
                .orElseThrow(() -> new ResourceNotFoundException("None of the listed users were found"));
    }

    public List<CodeSnippet> getSubmissionsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        return codeSnippetRepository.findBySubmittedBy(user);
    }
}