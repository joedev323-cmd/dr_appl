package com.example.dr_appl.controller.patient;

import com.example.dr_appl.model.dto.AppointmentDTO;
import com.example.dr_appl.service.AppointmentService;
import com.example.dr_appl.exception.ResourceLockedException;
import com.example.dr_appl.service.DoctorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PatientBookController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    public PatientBookController(
            AppointmentService appointmentService,
            DoctorService doctorService) {

        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @GetMapping("/appointments/book")
    public String showBookingPage(Model model) {

        model.addAttribute(
            "availableDoctors",
            doctorService.findActiveDoctors()
        );

        model.addAttribute(
            "appointmentDTO",
            new AppointmentDTO()
        );

        return "pat-book";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(
            @ModelAttribute AppointmentDTO appointmentDTO,
            RedirectAttributes redirectAttributes) {

        try {

            appointmentService.secureBooking(appointmentDTO);

            redirectAttributes.addFlashAttribute(
                "message",
                "Appointment secured successfully!"
            );

        } catch (ResourceLockedException e) {

            redirectAttributes.addFlashAttribute(
                "error",
                e.getMessage()
            );

        } catch (Exception e) {

            e.printStackTrace();

            redirectAttributes.addFlashAttribute(
                "error",
                "Booking failed: " + e.getMessage()
            );
        }

        return "redirect:/pat-appointmt";
    }
}