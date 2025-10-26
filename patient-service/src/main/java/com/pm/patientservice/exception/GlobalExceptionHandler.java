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
 * GlobalExceptionHandler is a centralized place to handle all exceptions 
 * that occur across the entire application.
 * 
 * It helps in keeping controller classes clean and consistent 
 * by returning standardized error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Logger instance for recording exceptions
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors that occur when @Valid fails in controller methods.
     *
     * @param ex the MethodArgumentNotValidException thrown during validation
     * @return ResponseEntity containing a map of field names and their corresponding error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract all validation errors and store field name with the corresponding error message
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        // Return 400 Bad Request with the error details
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles the case when a user tries to register with an email that already exists in the system.
     *
     * @param ex the EmailAlreadyExistsException thrown when a duplicate email is detected
     * @return ResponseEntity containing a standardized error message
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        // Log the exception as a warning
        log.warn("Email Already Exists {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "email already exists");

        // Return 400 Bad Request with the message
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles the case when a patient record is not found in the system.
     *
     * @param ex the PatientNotFoundException thrown when patient lookup fails
     * @return ResponseEntity containing an informative error message
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {

        // Log the exception as a warning for debugging and tracking
        log.warn("Patient Not Found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "patient you searched for could not be found in system");

        // Return 400 Bad Request with the error details
        return ResponseEntity.badRequest().body(errors);
    }
}
