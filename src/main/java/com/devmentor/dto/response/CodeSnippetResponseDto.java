package com.devmentor.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeSnippetResponseDto {
    private String title;
    private String language;
    private String content;
    private String submittedBy;
    private LocalDateTime submittedAt;
}
