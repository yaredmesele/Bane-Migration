package com.example.legacycrm.exception;

import com.example.legacycrm.api.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ApiError> handleClientNotFound(
            ClientNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(ClientAlreadyMigratedException.class)
    public ResponseEntity<ApiError> handleClientAlreadyMigrated(
            ClientAlreadyMigratedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> "%s %s".formatted(error.getField(), error.getDefaultMessage()))
                        .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, new IllegalArgumentException(message), request);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> handleBindException(
            BindException ex, HttpServletRequest request) {
        String message =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> "%s %s".formatted(error.getField(), error.getDefaultMessage()))
                        .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, new IllegalArgumentException(message), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {
        String message =
                ex.getConstraintViolations().stream()
                        .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                        .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, new IllegalArgumentException(message), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    private ResponseEntity<ApiError> buildResponse(
            HttpStatus status, Exception ex, HttpServletRequest request) {
        ApiError error =
                ApiError.builder()
                        .timestamp(Instant.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity.status(status).body(error);
    }
}

