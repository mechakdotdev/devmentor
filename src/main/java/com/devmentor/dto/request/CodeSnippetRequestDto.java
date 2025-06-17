package com.devmentor.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeSnippetRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Language is required")
    private String language;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Username is required")
    private String username;
}