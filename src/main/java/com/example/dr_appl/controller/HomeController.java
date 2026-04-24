package com.example.dr_appl.controller;

import com.example.dr_appl.model.entity.Appointment;
import com.example.dr_appl.model.entity.Room;
import com.example.dr_appl.model.enums.DoctorStatus;
import com.example.dr_appl.model.enums.RoomStatus;
import com.example.dr_appl.repository.*;
import com.example.dr_appl.service.AppointmentService;
 

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final DoctorRepository doctorRepo;
    private final RoomRepository roomRepo;
    private final AppointmentService appointmentService;



    public HomeController(DoctorRepository doctorRepo, RoomRepository roomRepo, AppointmentService appointmentService) {
        this.doctorRepo = doctorRepo;
        this.roomRepo = roomRepo;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/")
    public String index(Model model) {
       
        java.time.LocalDate today = java.time.LocalDate.now();
        Room firstRoom = roomRepo.findAll().stream().findFirst().orElse(null);

        List<LocalTime> timeSlots = appointmentService.generateAvailableSlots(today,firstRoom);
        model.addAttribute("slots", timeSlots);
        model.addAttribute("doctors", doctorRepo.findByStatus(DoctorStatus.FREE));
        model.addAttribute("rooms", roomRepo.findByRoomStatus(RoomStatus.FREE));
        model.addAttribute("appointment", new Appointment());
        return "index";
    }
}
