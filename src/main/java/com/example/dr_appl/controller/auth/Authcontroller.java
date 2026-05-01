package com.example.dr_appl.controller.auth;

import com.example.dr_appl.model.User;
import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.entity.Patient;
import com.example.dr_appl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Authcontroller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
   
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

@Autowired
private PasswordEncoder passwordEncoder; // This pulls the BCrypt bean from your SecurityConfig

@PostMapping("/signup")
public String registerUser(@ModelAttribute("user") User user) {
    // 1. Prepare Identity & Security
    user.setRole("PATIENT");
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // 2. Create the Patient Profile object
    Patient patientProfile = new Patient();
    
    // 3. Establish the Two-Way Link
    // Link User -> Patient
    user.setPatient(patientProfile); 
    // Link Patient -> User (This sets the foreign key user_id in the DB)
    patientProfile.setUser(user);
 
    userRepository.save(user);

    return "redirect:/login?success";
}
@GetMapping("/doctors/register")
public String showDoctorForm(Model model) {
    model.addAttribute("user", new User()); 
    return "register-doctor";
}
@PostMapping("/doctors/register")
public String registerDoc(@ModelAttribute("user") User user) {
    user.setRole("DOCTOR");
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Doctor doctor = new Doctor();
    doctor.setUser(user);

    user.setDoctor(doctor); 
    userRepository.save(user);

    return "redirect:/doctors?success=true";
}
 @PostMapping("/login?logout")
    public String Logut(){
        return "redirect :/login?logout";
    }
}
