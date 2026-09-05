package com.example.dr_appl.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication; // Needed for 'Authentication'
import com.example.dr_appl.model.User;  
import com.example.dr_appl.repository.UserRepository;  

@Controller
public class DashboardController {

    private UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    } 
    
    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication, Model model) {
        
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        model.addAttribute("user", user);

        switch (user.getRole()) {
            case "ADMIN":
                // Admin sees the total system overview
                model.addAttribute("totalDoctors", userRepository.countByRole("DOCTOR"));
                model.addAttribute("totalPatients", userRepository.countByRole("PATIENT"));
             return "dashboard";  
            
            case "DOCTOR":
                return "doctor-dashboard";
            
            case "PATIENT":
                return "patient-dashboard";
            
            default:
                return "redirect:/login?error";
        }
   
    }
    
}