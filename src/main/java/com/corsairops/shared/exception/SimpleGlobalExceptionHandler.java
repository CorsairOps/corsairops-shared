package com.corsairops.shared.exception;

import com.corsairops.shared.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

public class SimpleGlobalExceptionHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Log log = LogFactory.getLog(SimpleGlobalExceptionHandler.class);

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex) {

        String message;
        try {
            ErrorResponse errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class);
            message = errorResponse.getMessage();
        } catch (Exception e) {
            message = ex.getStatusText();
        }

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatusCode().value(),
                ex.getStatusText(),
                message,
                LocalDateTime.now().toString()
        );
        log(ex);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "Bad Request",
                "Malformed JSON request",
                LocalDateTime.now().toString()
        );
        log(ex);
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "Bad Request",
                errorMessage,
                ex.getAllErrors(),
                java.time.LocalDateTime.now().toString()
        );
        log(ex);
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "Bad Request",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        log(ex);
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
        log(ex);
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
        log(ex);
        return ResponseEntity.status(500).body(errorResponse);
    }

    protected void log(Exception ex) {
        log.error(ex.getMessage(), ex);
    }
}