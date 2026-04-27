package com.example.dr_appl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {
     @GetMapping("/rooms")
    public String room(){
        return "rooms";
    }
}
