package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating or updating a Patient entity.
 * This DTO includes basic validations
 */
public class PatientRequestDTO {

    /**
     * Full name of the patient.
     * Must not be blank and has a maximum length of 100 characters.
     */
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    /**
     * Email address of the patient.
     * Must not be blank and should follow a valid mail format.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Residential address of the patient.
     * Must not be blank.
     */
    @NotBlank(message = "Address is required")
    private String address;

    /**
     * Date of birth of the patient.
     * Should be provided in string format and must not be blank.
     */
    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    /**
     * Registered date of the patient.
     * This field is required only when creating a new patient (conditional validation using group).
     */
    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered date is required")
    private String registeredDate;

    // Getter and setter for name
    public @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String getName() {
        return name;
    }

    public void setName(
            @NotBlank(message = "Name is required")
            @Size(max = 100, message = "Name cannot exceed 100 characters") String name) {
        this.name = name;
    }

    // Getter and setter for email address
    public @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String getEmail() {
        return email;
    }

    public void setEmail(
            @NotBlank(message = "Email is required")
            @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    // Getter and setter for address
    public @NotBlank(message = "Address is required") String getAddress() {
        return address;
    }

    public void setAddress(
            @NotBlank(message = "Address is required") String address) {
        this.address = address;
    }

    // Getter and setter for date of birth
    public @NotBlank(message = "Date of birth is required") String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(
            @NotBlank(message = "Date of birth is required") String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Getter and setter for registered date
    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
