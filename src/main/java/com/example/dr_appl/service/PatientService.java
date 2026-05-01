package com.example.dr_appl.service;

import com.example.dr_appl.model.entity.Patient;
import com.example.dr_appl.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient getPatientByEmail(String email) {
        return patientRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient record not found for: " + email));
    }

    public void updatePatientProfile(String email, Patient updatedData) {
        Patient existing = getPatientByEmail(email);
        
        // Update the fields
        existing.setFullname(updatedData.getFullname());
        existing.setdob(updatedData.getdob());
        existing.setGender(updatedData.getGender());
        existing.setInsuranceProvider(updatedData.getInsuranceProvider());
        existing.setMedicalHistory(updatedData.getMedicalHistory());

        patientRepository.save(existing);
    }
}