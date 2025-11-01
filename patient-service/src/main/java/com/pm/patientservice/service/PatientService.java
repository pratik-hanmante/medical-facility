package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    // Injecting the PatientRepository to interact with the database
    private final PatientRepository patientRepository;

    // Constructor injection (preferred for immutability and easier testing)
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Fetch all patients from the database and convert them into response DTOs.
     * This is used by the controller to return a clean, safe response object
     * instead of exposing the entity model directly.
     */
    public List<PatientResponseDTO> getPatients() {
        return patientRepository.findAll()
                .stream()
                .map(PatientMapper::toDTO) // Convert each Patient entity to a DTO
                .toList();
    }

    /**
     * Create a new patient record.
     * 1. Check if the email already exists to maintain uniqueness.
     * 2. Convert the incoming DTO to a Patient entity.
     * 3. Save it to the DB
     * 4. Return the saved patient as a response DTO.
     */
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        // Prevent duplicate email registrations
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exists: " + patientRequestDTO.getEmail()
            );
        }

        // Convert DTO to entity and save in database
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        // Return saved patient as DTO (used by controller to send JSON response)
        return PatientMapper.toDTO(newPatient);
    }

    /**
     * Update an existing patient's details.
     * 1. Check if the patient exists by ID.
     * 2. Ensure the updated email isn’t already used by another patient.
     * 3. Update all relevant fields.
     * 4. Save changes and return the updated DTO.
     */
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        // Find existing patient or throw error if not found
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));

        // Check if the new email belongs to another patient
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email already exists: " + patientRequestDTO.getEmail()
            );
        }

        // Update fields from request DTO
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        // Save the updated entity and map to DTO
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    /**
     * Delete a patient record by ID.
     * 1. Validate if the patient exists.
     * 2. Perform delete operation.
     * 3. If patient doesn’t exist, throw an exception.
     */
    public void deletePatient(UUID id) {
        // Check if patient exists before deleting
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }

        // Delete record from DB
        patientRepository.deleteById(id);
    }
}
