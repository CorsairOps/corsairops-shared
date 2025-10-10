package com.corsairops.shared.exception;

import org.springframework.http.HttpStatus;

public class EncryptionException extends HttpResponseException {
    private final Exception cause;
    public EncryptionException(String message, HttpStatus status, Exception cause) {
        super(message, status);
        this.cause = cause;
    }

    @Override
    public Exception getCause() {
        return cause;
    }
}