package com.example.Contractor.Exception;

import com.example.Contractor.Controller.ContractorControllerImpl;
import com.example.Contractor.Controller.CountryControllerImpl;
import com.example.Contractor.Controller.IndustryControllerImpl;
import com.example.Contractor.Controller.OrgFormControllerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions from controller classes.
 */
@RestControllerAdvice(assignableTypes = {ContractorControllerImpl.class,
        CountryControllerImpl.class, IndustryControllerImpl.class, OrgFormControllerImpl.class})
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
