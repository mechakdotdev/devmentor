package com.devmentor.controller;

import com.devmentor.dto.request.UserRegistrationDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.mapper.UserMapper;
import com.devmentor.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDto dto) {
        User registeredUser = userService.register(UserMapper.toEntity(dto));

        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{username}/submissions")
    public ResponseEntity<List<CodeSnippet>> getSubmissions(@PathVariable String username) {
        List<CodeSnippet> codeSnippets = userService.getSubmissionsByUsername(username);

        return ResponseEntity.ok(codeSnippets);
    }
}