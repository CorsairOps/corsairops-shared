package com.corsairops.shared.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class HttpResponseException extends RuntimeException {

    private HttpStatus status;
    private LocalDateTime timestamp;

    public HttpResponseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}