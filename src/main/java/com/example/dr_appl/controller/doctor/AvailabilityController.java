package com.example.dr_appl.controller.doctor;

import com.example.dr_appl.model.User;
import com.example.dr_appl.model.entity.*;
import com.example.dr_appl.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/doctor/availability")
public class AvailabilityController{

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. The Main Page (GET)
    @GetMapping
    public String showAvailabilityPage(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        Doctor currentDoctor = user.getDoctor(); 

        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("existingSlots", availabilityRepository.findByDoctorOrderByStartTimeAsc(currentDoctor));
        model.addAttribute("user", user); 
        
        return "doc-availability"; // Returns the HTML template
    }

    // 2. The API for the Calendar (JSON)
    @GetMapping("/events")
    @ResponseBody
    public List<Map<String, Object>> getCalendarEvents(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        List<Availability> slots = availabilityRepository.findByDoctorOrderByStartTimeAsc(user.getDoctor());

        return slots.stream().map(slot -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", slot.getRoom().getRoomName());
            event.put("start", slot.getStartTime().toString());
            event.put("end", slot.getEndTime().toString());
            event.put("color", slot.isBooked() ? "#dc3545" : "#28a745"); // Red if booked, Green if free
            return event;
        }).collect(Collectors.toList());
    }

    // 3. The Logic (POST)
    @PostMapping("/generate")
    public String generateSlots(
            Principal principal,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam("roomId") Long roomId,
            @RequestParam("duration") int duration) {

        User user = userRepository.findByEmail(principal.getName());
        Doctor currentDoctor = user.getDoctor();
        Room selectedRoom = roomRepository.findById(roomId).orElseThrow();

        LocalDateTime current = LocalDateTime.of(date, startTime);
        LocalDateTime shiftEnd = LocalDateTime.of(date, endTime);

        while (current.plusMinutes(duration).isBefore(shiftEnd) || current.plusMinutes(duration).isEqual(shiftEnd)) {
            Availability slot = new Availability();
            slot.setDoctor(currentDoctor);
            slot.setRoom(selectedRoom);
            slot.setStartTime(current);
            slot.setEndTime(current.plusMinutes(duration));
            slot.setBooked(false);

            availabilityRepository.save(slot);
            current = current.plusMinutes(duration);
        }

        // CORRECT ROUTING: Redirect back to the @GetMapping URL
        return "redirect:/doctor/availability";
    }

@PostMapping("/delete/{id}")
public String deleteSlot(@PathVariable("id") Long id) {
    availabilityRepository.deleteById(id);
    return "redirect:/doctor/availability";
}

 @GetMapping("/api/events")
@ResponseBody
public List<Map<String, Object>> getEvents(Principal principal) {
    User user = userRepository.findByEmail(principal.getName());
    List<Availability> slots = availabilityRepository.findByDoctorOrderByStartTimeAsc(user.getDoctor());
    
    return slots.stream().map(slot -> {
        Map<String, Object> event = new HashMap<>();
       // event.put("id", slot.getAvailabilityId()); // Use your actual ID field name
        event.put("title", slot.getRoom().getRoomName());
        event.put("start", slot.getStartTime().toString());
        event.put("end", slot.getEndTime().toString());
        event.put("color", slot.isBooked() ? "#ff4444" : "#00C851");
        return event;
    }).collect(Collectors.toList());
}
}

 