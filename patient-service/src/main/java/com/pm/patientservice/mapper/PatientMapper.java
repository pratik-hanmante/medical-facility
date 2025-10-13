package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

/**
 * Mapper class for converting between Patient Entity and DTOs.
 * Ensures clean separation between API layer and database entity structure.
 */
public class PatientMapper {

    /**
     * Converts Patient entity (from database layer) to PatientResponseDTO (sent to client).
     * Maintains controlled exposure of data and formatting (e.g., converting LocalDate to String).
     */
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientDTO = new PatientResponseDTO();

        // Ensuring ID is exposed as String to keep response format clean and API-friendly
        patientDTO.setId(String.valueOf(patient.getId()));

        // Mapping basic fields directly from entity to DTO (data flowing from DB → Controller → Client)
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());

        // Converting LocalDate to String to maintain consistent API response format
        if (patient.getDateOfBirth() != null) {
            patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        } else {
            patientDTO.setDateOfBirth(null);
        }

        return patientDTO;
    }

    /**
     * Converts PatientRequestDTO (coming from client) to Patient entity (used for DB persistence).
     * Ensures incoming request data is transformed into a valid domain model.
     */
    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();

        // Mapping user input into domain model (data flowing from Client → Controller → DB)
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());

        // Parsing date strings into LocalDate for proper database storage format
        if (patientRequestDTO.getDateOfBirth() != null) {
            patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        }
        if (patientRequestDTO.getRegisteredDate() != null) {
            patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        }

        return patient;
    }
}
