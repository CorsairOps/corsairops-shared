package com.corsairops.shared.exception;

import com.corsairops.shared.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class SimpleGlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "Bad Request",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(HttpResponseException.class)
    public ResponseEntity<ErrorResponse> handleHttpResponseException(HttpResponseException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                500,
                "Internal Server Error",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(500).body(errorResponse);
    }
}