package com.example.dr_appl.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.dr_appl.model.User;
import com.example.dr_appl.model.entity.*;
import com.example.dr_appl.repository.*;

@Controller
@RequestMapping("/doctor/availability")
public class AvailabilityController {

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
        
        return "doc-availability"; 
    }

    // 2. The API for the Calendar (JSON) - Specific to the logged-in Doctor
    @GetMapping("/api/events")
    @ResponseBody
    public List<Map<String, Object>> getEvents(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        List<Availability> slots = availabilityRepository.findByDoctorOrderByStartTimeAsc(user.getDoctor());
        
        return formatEvents(slots);
    }

    // 3. Global Room API - To see if a specific room is busy (regardless of doctor)
    @GetMapping("/api/room-events/{roomId}")
    @ResponseBody
    public List<Map<String, Object>> getRoomEvents(@PathVariable Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        List<Availability> slots = availabilityRepository.findByRoomOrderByStartTimeAsc(room);
        return formatEvents(slots);
    }

    // 4. The Logic (POST) - With Multi-Doctor/Multi-Room Validation
    @PostMapping("/generate")
    public String generateSlots(
            Principal principal,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam("roomId") Long roomId,
            @RequestParam("duration") int duration,
            RedirectAttributes redirectAttributes) {

        User user = userRepository.findByEmail(principal.getName());
        Doctor currentDoctor = user.getDoctor();
        Room selectedRoom = roomRepository.findById(roomId).orElseThrow();

        LocalDateTime rangeStart = LocalDateTime.of(date, startTime);
        LocalDateTime rangeEnd = LocalDateTime.of(date, endTime);

        
      // 1. Change the call to use the boolean method
    boolean roomOccupied = availabilityRepository.isRoomOccupied(selectedRoom, rangeStart, rangeEnd);

    // 2. Update the 'if' statement to check the boolean
    if (roomOccupied) {
        redirectAttributes.addFlashAttribute("errorMessage", "The room is already occupied during this time.");
    return "redirect:/doctor/availability";
        }

        // Check 2: Is the current doctor already busy in a different room?
        boolean doctorBusy = availabilityRepository.isDoctorBusy(currentDoctor, rangeStart, rangeEnd);
        if (doctorBusy) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are already scheduled in another room during this time.");
            return "redirect:/doctor/availability";
        }

        // --- GENERATION LOGIC ---
        LocalDateTime current = rangeStart;
        while (current.plusMinutes(duration).isBefore(rangeEnd) || current.plusMinutes(duration).isEqual(rangeEnd)) {
            Availability slot = new Availability();
            slot.setDoctor(currentDoctor);
            slot.setRoom(selectedRoom);
            slot.setStartTime(current);
            slot.setEndTime(current.plusMinutes(duration));
            slot.setBooked(false);

            availabilityRepository.save(slot);
            current = current.plusMinutes(duration);
        }

        redirectAttributes.addFlashAttribute("successMessage", "Slots generated successfully!");
        return "redirect:/doctor/availability";
    }

    @PostMapping("/delete/{id}")
    public String deleteSlot(@PathVariable("id") Long id) {
        availabilityRepository.deleteById(id);
        return "redirect:/doctor/availability";
    }

    @DeleteMapping("/api/delete/{id}")
    @ResponseBody
    public void deleteSlotApi(@PathVariable Long id) {
        availabilityRepository.deleteById(id);
    }

    /**
     * Helper method to convert Availability objects into FullCalendar-friendly Maps.
     */
    private List<Map<String, Object>> formatEvents(List<Availability> slots) {
        return slots.stream().map(slot -> {
            Map<String, Object> event = new HashMap<>();
            event.put("id", slot.getId()); // Make sure your Entity has getId()
            event.put("title", slot.getRoom().getRoomName() + " (Dr. " + slot.getDoctor().getName() + ")");
            event.put("start", slot.getStartTime().toString());
            event.put("end", slot.getEndTime().toString());
            event.put("color", slot.isBooked() ? "#ff4444" : "#00C851");
            return event;
        }).collect(Collectors.toList());
    }
}

 