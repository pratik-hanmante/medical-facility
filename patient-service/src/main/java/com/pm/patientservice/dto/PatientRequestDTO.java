package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) used for creating or updating patient information.
 * Includes validation annotations to ensure data integrity.
 */
public class PatientRequestDTO {

    /**
     * Full name of the patient.
     * - Cannot be blank.
     * - Maximum length allowed is 100 characters.
     */
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    /**
     * Email address of the patient.
     * - Cannot be blank.
     * - Must be in a valid email format.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Residential address of the patient.
     * - Cannot be blank.
     */
    @NotBlank(message = "Address is required")
    private String address;

    /**
     * Date of birth of the patient.
     * - Cannot be blank.
     * - Provided as a String (can later be parsed to LocalDate).
     */
    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    /**
     * Date when the patient was registered.
     * - Required only during creation (validated through {@link CreatePatientValidationGroup}).
     * - Optional during update.
     */
    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered date is required")
    private String registeredDate;

   

    /**
     * Returns the patient's name.
     * @return name of the patient
     */
    public @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String getName() {
        return name;
    }

    /**
     * Sets the patient's name.
     * @param name must not be blank and should not exceed 100 characters
     */
    public void setName(
            @NotBlank(message = "Name is required")
            @Size(max = 100, message = "Name cannot exceed 100 characters") String name) {
        this.name = name;
    }

    /**
     * Returns the patient's email address.
     * @return valid email address of the patient
     */
    public @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String getEmail() {
        return email;
    }

    /**
     * Sets the patient's email address.
     * @param email must be a valid email format and not blank
     */
    public void setEmail(
            @NotBlank(message = "Email is required")
            @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    /**
     * Returns the residential address of the patient.
     * @return address of the patient
     */
    public @NotBlank(message = "Address is required") String getAddress() {
        return address;
    }

    /**
     * Sets the residential address of the patient.
     * @param address cannot be blank
     */
    public void setAddress(
            @NotBlank(message = "Address is required") String address) {
        this.address = address;
    }

    /**
     * Returns the date of birth of the patient.
     * @return date of birth as String
     */
    public @NotBlank(message = "Date of birth is required") String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the patient.
     * @param dateOfBirth cannot be blank
     */
    public void setDateOfBirth(
            @NotBlank(message = "Date of birth is required") String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the registered date of the patient.
     * @return registered date
     */
    public String getRegisteredDate() {
        return registeredDate;
    }

    /**
     * Sets the registered date of the patient.
     * @param registeredDate required only during patient creation
     */
    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
