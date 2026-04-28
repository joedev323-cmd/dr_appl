package com.example.dr_appl.controller;

import com.example.dr_appl.model.User;
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
    // 1. Hash the password
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    
    // 2. Set the hashed password back into the user object
    user.setPassword(encodedPassword);
    
    // 3. Save to DB
    userRepository.save(user);
    
    return "redirect:/login?success";
}
}
