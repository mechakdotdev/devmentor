package com.devmentor.controller;

import com.devmentor.dto.request.UserRegistrationDto;
import com.devmentor.entity.User;
import com.devmentor.mapper.UserMapper;
import com.devmentor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto dto) {
        User registeredUser = userService.register(UserMapper.toEntity(dto));

        return ResponseEntity.ok(registeredUser);
    }
}