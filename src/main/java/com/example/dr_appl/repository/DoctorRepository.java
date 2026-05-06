package com.example.dr_appl.repository;

import com.example.dr_appl.model.entity.Doctor;
import com.example.dr_appl.model.enums.AdminControlStatus;
import com.example.dr_appl.model.enums.DoctorIntent;
 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    // Finds doctors ready to "execute" a task
   List<Doctor> findByAdminStatus(AdminControlStatus status);
   List<Doctor> findByDoctorIntent(DoctorIntent AVAILABLE);
    // Finds doctors by their specific "processing capability"
    List<Doctor> findBySpecialization(String Specialization);
    
    Optional<Doctor> findByUserEmail(String email);

 
}
