 package com.example.dr_appl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // FIXED: Correct import for web data
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dr_appl.repository.DoctorRepository;
import com.example.dr_appl.repository.RoomRepository;
import com.example.dr_appl.service.AppointmentService;
import com.example.dr_appl.model.enums.DoctorStatus; // ADDED: Import for Enums
import com.example.dr_appl.model.enums.RoomStatus;   // ADDED: Import for Enums

@Controller
public class HomeController {

    // @Autowired private AppointmentService appointmentService;
    // @Autowired private DoctorRepository doctorRepository;
    // @Autowired private RoomRepository roomRepository;

    // @GetMapping("/dashboard")
    // public String dashboard(Model model) {
    //     // Ensure these methods (countByStatus, etc.) exist in your repositories/services
    //    // model.addAttribute("totalAppointments", appointmentService.countAll());
    //     model.addAttribute("doctors", doctorRepository.findByStatus(DoctorStatus.FREE));
    //     model.addAttribute("rooms", roomRepository.findByRoomStatus(RoomStatus.OCCUPIED));
    //     model.addAttribute("appointment", appointmentService);
        
    //     return "dashboard";
    // }
    // @PostMapping("/dashboard")
    // public String dash(){
    //     return "dashboard";
    // }

    @GetMapping("/")
    public String ind(){
        return "index";
    }

    @GetMapping("/doctors")
    public String doc(){
        return "doctors";
    }

  
}
