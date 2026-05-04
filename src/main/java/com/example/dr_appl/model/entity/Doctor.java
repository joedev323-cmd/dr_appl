package com.example.dr_appl.model.entity;
 
import java.util.ArrayList;
import java.util.List;

import com.example.dr_appl.model.User;
import com.example.dr_appl.model.enums.DoctorStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String specialization;
    private String yearsofExperience;
    @Enumerated(EnumType.STRING)
    private DoctorStatus status;
    
    @OneToOne
   @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // Links back to the User identity
      
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Availability> schedules = new ArrayList<>();    
    public Doctor(){}
    
    public Doctor(Long id, String name, String specialization, String yearsofExperience, DoctorStatus status,
            User user) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.yearsofExperience = yearsofExperience;
        this.status = status;
        this.user = user;
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

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getYearsofExperience() {
        return yearsofExperience;
    }

    public void setYearsofExperience(String yearsofExperience) {
        this.yearsofExperience = yearsofExperience;
    }

    public DoctorStatus getStatus() {
        return status;
    }

    public void setStatus(DoctorStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
     
    
}
