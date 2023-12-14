package com.gira.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ErrorResponse {
    private String timestamp;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
    }

    public ErrorResponse(String message, Map<String, String> errors) {
        this(message);
        this.errors = errors;
    }
}
