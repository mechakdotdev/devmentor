package com.devmentor.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ValidationErrorResponseDto extends ErrorResponseDto {
    private Map<String, String> errors;

    public ValidationErrorResponseDto(LocalDateTime timestamp, String status, String message, Map<String, String> errors) {
        super(timestamp, status, message);
        this.errors = errors;
    }
}
