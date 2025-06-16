package com.devmentor.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeSnippetRequestDto {
    private String title;
    private String language;
    private String content;
    private String username;
}