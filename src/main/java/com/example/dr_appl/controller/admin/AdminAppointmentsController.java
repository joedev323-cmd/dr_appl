package com.example.dr_appl.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminAppointmentsController {
    @GetMapping("/appointmt")
    public String getAppontsum() {
        return "admin-appntmt";
    }
    

}
