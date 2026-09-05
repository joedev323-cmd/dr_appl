package com.example.dr_appl.controller.doctor;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.dr_appl.model.User;
import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.entity.Patient;

import com.example.dr_appl.repository.UserRepository;
import com.example.dr_appl.repository.AppointmentRepository;

@Controller
public class DoctorPatientsController {
    // 2. Inject the repositories
    private UserRepository userRepository;

    private AppointmentRepository AppointmentRepository;
    
    public DoctorPatientsController(UserRepository userRepository,
            com.example.dr_appl.repository.AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        AppointmentRepository = appointmentRepository;
    }

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