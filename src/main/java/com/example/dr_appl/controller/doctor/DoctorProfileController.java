package com.example.dr_appl.controller.doctor;

import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.dr_appl.repository.*;

import java.security.Principal;

@Controller
@RequestMapping("/doctor/profile")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorProfileController {

    @Autowired
    private DoctorRepository doctorRepository;

    // 1. View Profile
    @GetMapping
    public String viewProfile(Model model, Principal principal) {
        Doctor doctor = getLoggedInDoctor(principal);
        model.addAttribute("doctor", doctor);
        return "doc-profile"; 
    }

    // 2. Show Edit Form
    @GetMapping("/edit")
    public String showEditForm(Model model, Principal principal) {
        Doctor doctor = getLoggedInDoctor(principal);
        model.addAttribute("doctor", doctor);
        return "profile-edit"; 
    }

    // 3. Process Update
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("doctor") Doctor doctorData, 
                                Principal principal, 
                                RedirectAttributes redirectAttributes) {
        Doctor existingDoctor = getLoggedInDoctor(principal);

        // Update fields from your Entity
        existingDoctor.setName(doctorData.getName());
        existingDoctor.setSpecialization(doctorData.getSpecialization());
        existingDoctor.setYearsofExperience(doctorData.getYearsofExperience());
        existingDoctor.setStatus(doctorData.getStatus());
        
        // Note: Usually, you don't let doctors change their own 'status' 
        // or 'user' link via a profile form for security/admin reasons.

        doctorRepository.save(existingDoctor);
        
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        return "redirect:/doctor/profile";
    }

    // Helper method to keep code DRY
    private Doctor getLoggedInDoctor(Principal principal) {
        String email = principal.getName();
        return doctorRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found for: " + email));
    }
}