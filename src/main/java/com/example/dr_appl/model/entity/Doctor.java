package com.example.dr_appl.model.entity;
 
import com.example.dr_appl.model.enums.DoctorStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String specialization;
    private String yearsofExperience;
    @Enumerated(EnumType.STRING)
    private DoctorStatus status;
    
    
    public Doctor(){}
    
    public Doctor(String name, String Specialization, String YearsofExperiece, DoctorStatus status) {
        this.name = name;
        specialization = Specialization;
        yearsofExperience = YearsofExperiece;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String Specialization) {
        specialization = Specialization;
    }

    public String getYearsofExperiece() {
        return yearsofExperience;
    }

    public void setYearsofExperiece(String YearsofExperience) {
        yearsofExperience = YearsofExperience;
    }

    public DoctorStatus getStatus() {
        return status;
    }

    public void setStatus(DoctorStatus status) {
        this.status = status;
    }
    
    
    
}
