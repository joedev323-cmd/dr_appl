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

                // Public
                .requestMatchers(
                    "/",
                    "/signup",
                    "/login",
                    "/css/**",
                    "/img/**"
                ).permitAll()

                // Admin
                .requestMatchers(
                    "/doctors/**",
                    "/rooms/**",
                    "/appointmt/**"
                ).hasRole("ADMIN")

                // Doctor
                .requestMatchers(
                    "/doctor/**"
                ).hasRole("DOCTOR")

                // Patient
                .requestMatchers(
                    "/appointments/**",
                    "/pat-appointmt",
                    "/presc",
                    "/profile/**"
                ).hasRole("PATIENT")

                // Everything else
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}