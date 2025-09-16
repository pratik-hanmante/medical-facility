package com.pm.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    // Logger instance for logging exception
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract each field error and put into the map with field name as key
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        // Log the exception with warning level
        log.warn("Email Already Exists {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "email already exists");

        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {

        // Log the exception with the as expected warning
        log.warn("Patient Not Found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "patient you searched for could not be found in system");

        return ResponseEntity.badRequest().body(errors);
    }
}
