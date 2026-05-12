package com.example.dr_appl.model.entity;
 
import com.example.dr_appl.model.enums.*;
import com.example.dr_appl.model.User;

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
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We keep specialization and experience here as they are unique to the medical role
    private String specialization;
    private String yearsofExperience;

    // 1. ADMIN POWER: Global switch for system access
    @Enumerated(EnumType.STRING)
    private AdminControlStatus adminStatus = AdminControlStatus.ACTIVE;
    private String name;
  
    // 2. DOCTOR POWER: Personal availability intent
    @Enumerated(EnumType.STRING)
    private DoctorIntent doctorIntent = DoctorIntent.AVAILABLE;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; 
      
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Availability> schedules = new ArrayList<>();    

    public Doctor(){}

    @Transient
    public String getCalculatedStatus(boolean hasActiveAppointment) {
        if (this.adminStatus == AdminControlStatus.SUSPENDED) return "System Suspended";
        if (this.doctorIntent == DoctorIntent.ON_LEAVE) return "On Leave";
        if (hasActiveAppointment) return "Busy (In Session)";
        return "Available";
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

    public AdminControlStatus getAdminStatus() { 
        return adminStatus; 
    }
    
    public void setAdminStatus(AdminControlStatus adminStatus) { 
        this.adminStatus = adminStatus; 
    }

    public DoctorIntent getDoctorIntent() { 
        return doctorIntent;
    }

    public void setDoctorIntent(DoctorIntent doctorIntent) { 
        this.doctorIntent = doctorIntent;
    }

    public User getUser() { 
        return user;
    }

    public void setUser(User user) { 
        this.user = user; 
    }

    public List<Availability> getSchedules() {
         return schedules; 
    }

    public void setSchedules(List<Availability> schedules) { 
        this.schedules = schedules; 
    }

}