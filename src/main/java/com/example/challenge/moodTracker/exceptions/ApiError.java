package com.example.challenge.moodTracker.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Formats all exception response in a uniform format
 */
public class ApiError {
    private String message;
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String uri;
    private List<Object> errors;

    private ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, Throwable ex, List<Object> errorsList) {
        this();
        this.status = status;
        this.message = ex.getLocalizedMessage();
        this.errors = errorsList;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ApiError(String message, Date timestamp) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUri() {
        return uri;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<Object> getErrors() {
        return errors;
    }
}