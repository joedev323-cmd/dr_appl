 package com.example.dr_appl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dr_appl.model.User;
import com.example.dr_appl.repository.UserRepository;

@SpringBootApplication
public class DrApplApplication {
    public static void main(String[] args) {
        SpringApplication.run(DrApplApplication.class, args);
        
    }
    @Bean
   CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
         if (userRepository.findByEmail("admin@healsync.com") == null) {
            User admin = new User();
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setEmail("admin@healsync.com");
            admin.setPassword(passwordEncoder.encode("admin123"));  
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println(">>> Initial Admin account created: admin@healsync.com / admin123");
        }
    };
}
}

