package com.example.dr_appl.service;

import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepo;

    public DoctorService(DoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public List<Doctor> findAll() {
        return doctorRepo.findAll();
    }
    
}