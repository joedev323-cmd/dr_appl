package com.example.dr_appl.controller.patient;

import com.example.dr_appl.model.entity.Patient;
import com.example.dr_appl.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class PatientProfileController {

    @Autowired
    private PatientService patientService;

    @GetMapping 
    public String editProfile(Model model, Principal principal) {
        // Fetch the existing patient data to pre-fill the form
        Patient patient = patientService.getPatientByEmail(principal.getName());
        model.addAttribute("patient", patient);
        return "profile-edit"; // Matches your HTML filename
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("patient") Patient patient, Principal principal) {
        patientService.updatePatientProfile(principal.getName(), patient);
        return "redirect:/dashboard?success=true";
    }
}