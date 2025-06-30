package com.devmentor.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ReviewCreateRequestDto {
    @NotNull(message = "Code snippet ID is required")
    private UUID codeSnippetId;
    @NotEmpty(message = "At least one reviewer email is required")
    private List<String> reviewers;
    @NotEmpty(message = "Feedback is required")
    private String feedback;
}
