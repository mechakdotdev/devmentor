package com.devmentor.exception;

import com.devmentor.dto.response.ErrorResponseDto;
import com.devmentor.dto.response.ValidationErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleValidation(MethodArgumentNotValidException ex) {
        var status = HttpStatus.BAD_REQUEST;
        var objectName = getFailedValidationObjectName(ex);
        var errors = getFieldErrors(ex);

        var response = new ValidationErrorResponseDto(
                LocalDateTime.now(),
                buildErrorString(status),
                "Validation failed for " + objectName,
                errors
        );

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneral(Exception ex) {
        logger.error("Unexpected exception caught", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(HttpStatus status, String message) {
        var response = new ErrorResponseDto(
                LocalDateTime.now(),
                buildErrorString(status),
                message
        );
        return new ResponseEntity<>(response, status);
    }

    private String buildErrorString(HttpStatus status) {
        return String.format("Error %s: %s", status.value(), status.getReasonPhrase());
    }

    private Map<String, String> getFieldErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return errors;
    }

    private String getFailedValidationObjectName(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getTarget() != null
                ? exception.getBindingResult().getTarget().getClass().getSimpleName()
                : "Unknown";
    }
}