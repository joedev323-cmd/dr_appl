package com.example.dr_appl.controller.admin;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.enums.AdminControlStatus;
import com.example.dr_appl.repository.DoctorRepository;
import org.springframework.ui.Model;
 

@Controller
@RequestMapping("/doctors") 
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository; // Assuming you have a JPA repository

    @GetMapping("/manage")
    public String showManageDoctorsPage(Model model) {
        // Fetch all doctors from the database
        List<Doctor> doctorList = doctorRepository.findAll();
        
        // This key "doctors" must match th:each="doc : ${doctors}" in your HTML
        model.addAttribute("doctors", doctorList);
        
        return "doctors"; // The name of your .html file
    }

    @PostMapping("/toggle-status/{id}")
   public String toggleDoctorStatus(@PathVariable Long id, RedirectAttributes ra) {
    Doctor doc = doctorRepository.findById(id).orElseThrow();
    
    if (doc.getAdminStatus() == AdminControlStatus.ACTIVE) {
        doc.setAdminStatus(AdminControlStatus.SUSPENDED);
        ra.addFlashAttribute("success", "Doctor suspended.");
    } else {
        doc.setAdminStatus(AdminControlStatus.ACTIVE);
        ra.addFlashAttribute("success", "Doctor restored to active status.");
    }
    
    doctorRepository.save(doc);
    return "redirect:/doctors/manage";
}

@GetMapping("/edit/{id}")
public String showEditForm(@PathVariable Long id, Model model) {
    // 1. Find the doctor
    Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid doctor Id:" + id));
    
    // 2. Add to model so the form can see the data
    model.addAttribute("doctor", doctor);
    
    // 3. Return the name of your edit HTML file (e.g., edit-doctor.html)
    return "edit-doctor"; 
}

@PostMapping("/update/{id}")
public String updateDoctor(@PathVariable Long id, @ModelAttribute("doctor") Doctor doctorDetails, RedirectAttributes ra) {
    Doctor existingDoctor = doctorRepository.findById(id).orElseThrow();
    
    // Update specific fields (Keep the Admin/Doctor status logic separate or include it here)
    existingDoctor.setSpecialization(doctorDetails.getSpecialization());
    existingDoctor.setYearsofExperience(doctorDetails.getYearsofExperience());
    
    // If you want to update the name (stored in the User entity)
    if (existingDoctor.getUser() != null && doctorDetails.getUser() != null) {
        existingDoctor.getUser().setFirstName(doctorDetails.getUser().getFirstName());
        existingDoctor.getUser().setLastName(doctorDetails.getUser().getLastName());
    }

    doctorRepository.save(existingDoctor);
    ra.addFlashAttribute("success", "Doctor details updated successfully!");
    
    return "redirect:/doctors/manage";
}
}