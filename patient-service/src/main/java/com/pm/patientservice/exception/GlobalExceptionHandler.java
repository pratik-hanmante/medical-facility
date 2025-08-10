package com.pm.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling exceptions across the whole application in one place.
 * This uses @ControllerAdvice to catch and process exceptions thrown by controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Logger instance for logging exception messages
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation exceptions when request body fields fail @Valid annotations.
     * This method collects all validation errors and returns them in a map format.
     *
     * @param ex MethodArgumentNotValidException thrown when validation fails
     * @return ResponseEntity with a map of field names and error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract each field error and put into the map with field name as key
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles custom EmailAlreadyExistsException when trying to register with an existing email.
     *
     * @param ex EmailAlreadyExistsException thrown when email is already taken
     * @return ResponseEntity with a user-friendly error message
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        // Log the exception with a warning level
        log.warn("Email Already Exists {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "email already exists");

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles custom PatientNotFoundException when a requested patient record is not found.
     *

     * @return ResponseEntity with a user-friendly error message
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {

        // Log the exception with a warning
        log.warn("Patient Not Found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "patient you searched for could not be found");

        return ResponseEntity.badRequest().body(errors);
    }
}
