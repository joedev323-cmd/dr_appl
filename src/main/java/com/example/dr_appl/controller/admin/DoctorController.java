package com.example.dr_appl.controller.admin;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/doctors/toggle-status/{id}")
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
}