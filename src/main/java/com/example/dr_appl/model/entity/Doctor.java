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
    private String Specialization;
    private String YearsofExperiece;
    @Enumerated(EnumType.STRING)
    private DoctorStatus status;
    
    
    public Doctor(){}
    
    public Doctor(String name, String specialization, String yearsofExperiece, DoctorStatus status) {
        this.name = name;
        Specialization = specialization;
        YearsofExperiece = yearsofExperiece;
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
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getYearsofExperiece() {
        return YearsofExperiece;
    }

    public void setYearsofExperiece(String yearsofExperiece) {
        YearsofExperiece = yearsofExperiece;
    }

    public DoctorStatus getStatus() {
        return status;
    }

    public void setStatus(DoctorStatus status) {
        this.status = status;
    }
    
    
    
}
