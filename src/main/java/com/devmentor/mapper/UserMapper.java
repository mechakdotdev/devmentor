package com.devmentor.mapper;

import com.devmentor.dto.request.UserRegistrationDto;
import com.devmentor.entity.User;

public class UserMapper {

    public static User toEntity(UserRegistrationDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .role(User.Role.valueOf(dto.getRole().toUpperCase()))
                .build();
    }
}