package com.financial.reactive.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(TransactionException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleTransactionException(TransactionException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid argument: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                LocalDateTime.now()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
    }

    public Mono<ErrorResponse> mapToErrorResponse(Throwable error) {
        if (error instanceof TransactionException txError) {
            return Mono.just(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    txError.getMessage(),
                    LocalDateTime.now()
            ));
        } else if (error instanceof IllegalArgumentException iae) {
            return Mono.just(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid argument: " + iae.getMessage(),
                    LocalDateTime.now()
            ));
        } else {
            return Mono.just(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred",
                    LocalDateTime.now()
            ));
        }
    }

    public record ErrorResponse(int status, String message, LocalDateTime timestamp) {}
}