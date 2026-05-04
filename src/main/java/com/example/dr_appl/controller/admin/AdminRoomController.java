package com.example.dr_appl.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dr_appl.model.User;
import com.example.dr_appl.model.entity.Room;
import com.example.dr_appl.repository.RoomRepository;
import com.example.dr_appl.repository.*;

@Controller
public class AdminRoomController {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/rooms")
    public String manageRooms(Model model, Principal principal) {
        // Get current admin user for the header
        User user = userRepository.findByEmail(principal.getName());
        
        model.addAttribute("user", user);
        model.addAttribute("rooms", roomRepository.findAll());
        return "admin-room"; // The new HTML file
    }

    @PostMapping("/rooms/add")
    public String addRoom(@RequestParam("roomName") String roomName, RedirectAttributes ra) {
        if (roomName.trim().isEmpty()) {
            ra.addFlashAttribute("error", "Room name cannot be empty!");
            return "redirect:/rooms";
        }
        
        Room room = new Room();
        room.setRoomName(roomName);
        roomRepository.save(room);
        
        ra.addFlashAttribute("success", "Room added successfully!");
        return "redirect:/rooms";
    }

    @PostMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes ra) {
        try {
            roomRepository.deleteById(id);
            ra.addFlashAttribute("success", "Room removed.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Cannot delete room; it is currently linked to doctor schedules.");
        }
        return "redirect:/rooms";
    }
}