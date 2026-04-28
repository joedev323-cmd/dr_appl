package com.example.dr_appl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
    // 1. Public Assets/Pages
    .requestMatchers("/", "/signup", "/login", "/style.css", "/img/**").permitAll()

    // 2. Admin Only (Management tasks)
    .requestMatchers("/doctors/**", "/rooms/**").hasRole("ADMIN")

    // 3. Doctor Only (Consultation tasks)
    // .requestMatchers("/doctor/schedule/**").hasRole("DOCTOR")

    // 4. Patient Only (Booking tasks)
    .requestMatchers("/appointmt/**").hasRole("PATIENT")

    // 5. Lock everything else to logged-in users
    .anyRequest().authenticated()
)
            .formLogin(form -> form
                .loginPage("/login") // Uses the  custom login.html
                .defaultSuccessUrl("/dashboard", true) // Where to go after login
                .permitAll()
            )
            .logout(logout -> logout
            .logoutUrl("/logout") // The URL that triggers logout
            .logoutSuccessUrl("/") // Where to go after logging out
            .invalidateHttpSession(true) // Delete the session
            .deleteCookies("JSESSIONID") // Clear the browser cookie
            .permitAll()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}