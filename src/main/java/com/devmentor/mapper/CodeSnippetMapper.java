package com.devmentor.mapper;

import com.devmentor.dto.CodeSnippetRequestDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;

import java.time.LocalDateTime;

public class CodeSnippetMapper {

    public static CodeSnippet toEntity(CodeSnippetRequestDto dto, User user) {
        return CodeSnippet.builder()
                .title(dto.getTitle())
                .language(dto.getLanguage())
                .content(dto.getContent())
                .submittedAt(LocalDateTime.now())
                .submittedBy(user)
                .build();
    }
}