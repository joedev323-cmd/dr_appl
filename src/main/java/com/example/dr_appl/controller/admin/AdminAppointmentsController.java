package com.example.dr_appl.controller.admin;

import com.example.dr_appl.model.entity.Appointment;
import com.example.dr_appl.service.AppointmentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminAppointmentsController {

    private final AppointmentService appointmentService;

    public AdminAppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointmt")
    public String getAppontsum(Model model) {

        List<Appointment> appointments = appointmentService.findAll();

        model.addAttribute("appointments", appointments);
        model.addAttribute("totalAppointments", appointmentService.countAll());

        return "admin-appntmt";
    }
}