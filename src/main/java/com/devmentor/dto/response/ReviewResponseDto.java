package com.devmentor.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ReviewResponseDto {
    private UUID id;
    private UUID codeSnippetId;
    private List<String> reviewers;
    private String feedback;
    private LocalDateTime createdAt;
}
