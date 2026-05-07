package com.example.dr_appl.controller.doctor;

import java.security.Principal;
import java.util.List;

// Correct Spring/JPA imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.dr_appl.model.User;
import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.entity.Patient;

// Your Repository imports
import com.example.dr_appl.repository.UserRepository;
import com.example.dr_appl.repository.AppointmentRepository;
@Controller // 1. Added this so Spring recognizes it as a web controller
public class DoctorPatientsController {

    @Autowired // 2. Inject the repositories
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository AppointmentRepository;

    @GetMapping("/doctor/patients")
    public String showPatients(Model model, Principal principal) {
        // 3. Call methods on the instance (lowercase), not the Class (uppercase)
        User user = userRepository.findByEmail(principal.getName());
        Doctor doctor = user.getDoctor();

        // 4. Using the injected repository instance
        List<Patient> patients = AppointmentRepository.findDistinctPatientsByDoctor(doctor);
        
        model.addAttribute("patients", patients);
        model.addAttribute("user", user); // Added this so your header (Dr. Name) works
        
        return "doc-patients";
    }
}