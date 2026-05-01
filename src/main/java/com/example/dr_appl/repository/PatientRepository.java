package com.example.dr_appl.repository;

import com.example.dr_appl.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // This allows us to find the patient record via the logged-in user's email
    Optional<Patient> findByUserEmail(String email);
}