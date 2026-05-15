package com.example.dr_appl.controller.patient;

import com.example.dr_appl.model.dto.AppointmentDTO;
import com.example.dr_appl.service.AppointmentService;
import com.example.dr_appl.exception.ResourceLockedException;
import com.example.dr_appl.service.DoctorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Required for GET mapping
import org.springframework.web.bind.annotation.GetMapping; // Required for GET mapping
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class PatientBookController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService; // Add this line

    // Update the constructor to include DoctorService
    public PatientBookController(AppointmentService appointmentService, DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @GetMapping("/appointments/book")
public String showBookingPage(Model model) {
    // 1. Get doctors for the dropdown
    model.addAttribute("availableDoctors", doctorService.findActiveDoctors());
    
    // 2. IMPORTANT: Pass an empty DTO so th:object="${appointmentDTO}" works
    model.addAttribute("appointmentDTO", new AppointmentDTO());
    
    return "pat-book"; 
}

    // 2. existing POST mapping
    @PostMapping("/appointments/book")
    public String bookAppointment(@ModelAttribute AppointmentDTO appointmentDTO, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.secureBooking(appointmentDTO);
            redirectAttributes.addFlashAttribute("message", "Appointment secured successfully!");
        } catch (ResourceLockedException e) {
            redirectAttributes.addFlashAttribute("error", "This slot was just taken. Please try another time.");
        } catch (Exception e) {
            // Good to have a catch-all for unexpected database issues
            redirectAttributes.addFlashAttribute("error", "Something went wrong. Please try again.");
        }
        return "redirect:/dashboard";
    }
}