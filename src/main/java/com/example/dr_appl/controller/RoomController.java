package com.example.dr_appl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RoomController {
     @GetMapping("/rooms")
    public String room(){
        return "rooms";
    }
    @PostMapping("/login?logout")
    public String Logut(){
        return "redirect :/login?logout";
    }
    
}
