package com.example.dr_appl.service;

import com.example.dr_appl.model.User;
import com.example.dr_appl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

 

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

   @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 1. Use your Repository to find by EMAIL
    User user = userRepository.findByEmail(username); 
    
    if (user == null) {
        throw new UsernameNotFoundException("User not found with email: " + username);
    }

    // 2. Map your fields correctly to the Security User
    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail()) // We use email as the "username" for login
            .password(user.getPassword())
            .roles(user.getRole())
            .build();
}
}