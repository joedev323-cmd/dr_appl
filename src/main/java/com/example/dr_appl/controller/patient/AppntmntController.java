package com.example.dr_appl.controller.patient;

import com.example.dr_appl.model.entity.Appointment;
import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.service.AppointmentService;
import com.example.dr_appl.service.DoctorService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppntmntController {

    private final DoctorService doctorService; // Use Service instead of Repo
    private final AppointmentService appointmentService;

    public AppntmntController(DoctorService doctorService, AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

  @GetMapping("/pat-appointmt")
public String showAppointmentDashboard(Model model) {
    java.time.LocalDate today = java.time.LocalDate.now();
    
    // 1. Fetch data from services
    List<Doctor> doctors = doctorService.findAll();
    List<Appointment> appts = appointmentService.findAll();

    // 2. The names MUST match the "th:text" and "th:each" in your HTML
    model.addAttribute("availableDoctors", doctors); // Fixed name
    model.addAttribute("myAppointments", appts);     // Added this
    
    // 3. Logic for the slots preview
    model.addAttribute("slots", appointmentService.generateAvailableSlots(today, null));

    return "pat-appointm";
}
}
