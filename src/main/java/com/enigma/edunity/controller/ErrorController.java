package com.enigma.edunity.controller;

import com.enigma.edunity.dto.response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse<?>> responseStatusException(
            ResponseStatusException exception
    ) {
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(exception.getStatusCode().value())
                .message(exception.getMessage())
                .build();

        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<?>> constraintViolationException(
            ConstraintViolationException exception
    ) {
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();

        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<?>> illegalArgumentException(
            IllegalArgumentException exception
    ) {
        CommonResponse<?> response = CommonResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();

        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonResponse<?>> dataIntegrityViolationException(
            DataIntegrityViolationException exception
    ) {
        CommonResponse.CommonResponseBuilder<Object> response = CommonResponse.builder();
        HttpStatus httpStatus;

        String rootCauseMessage = exception.getRootCause().getMessage();
        if (rootCauseMessage.contains("foreign key constraint")) {
            response.statusCode(HttpStatus.BAD_REQUEST.value());
            response.message("Cannot delete data because other table depend on it");
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (rootCauseMessage.contains("unique constraint")){
            if (rootCauseMessage.contains("email")) {
                response.statusCode(HttpStatus.CONFLICT.value());
                response.message("Email already exists");
            }
            else if (rootCauseMessage.contains("phone_number")) {
                response.statusCode(HttpStatus.CONFLICT.value());
                response.message("Phone number already exists");
            } else {
                response.statusCode(HttpStatus.CONFLICT.value());
                response.message("Duplicate entry for unique field");
            }
            httpStatus = HttpStatus.CONFLICT;
        } else {
            response.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.message("Internal Server Error");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(response.build());
    }
}
