package com.devmentor.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ErrorResponseDto {
    private LocalDateTime timestamp;
    private String status;
    private String message;
}
