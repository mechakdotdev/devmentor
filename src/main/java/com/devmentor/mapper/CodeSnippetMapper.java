package com.devmentor.mapper;

import com.devmentor.dto.request.CodeSnippetRequestDto;
import com.devmentor.dto.response.CodeSnippetResponseDto;
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

    public static CodeSnippetResponseDto toResponseDTO(CodeSnippet snippet) {
        return new CodeSnippetResponseDto(
                snippet.getTitle(),
                snippet.getLanguage(),
                snippet.getContent(),
                snippet.getSubmittedBy().getUsername(),
                snippet.getSubmittedAt()
        );
    }
}