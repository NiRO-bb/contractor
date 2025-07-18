package com.example.Contractor.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions from controller classes.
 */
@RestControllerAdvice("package com.example.Contractor.Controller")
public class ControllerExceptionHandler {

    /**
     * Catches RuntimeExceptions and handles them.
     *
     * @param exception catched exception
     * @return ResponseEntity with error message and http status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
