package com.example.dr_appl.controller.patient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PatientPrescriptionCOntroller {
    @GetMapping("/presc")
    public String presccon(){
        return "pat-prescript";
    }
}
