package com.example.dr_appl.controller.admin;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.repository.DoctorRepository;
import org.springframework.ui.Model;
 

@Controller
@RequestMapping("/doctors") // Or wherever your path is
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
}