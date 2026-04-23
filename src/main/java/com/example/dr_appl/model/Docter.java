package com.example.dr_appl.model;
 
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Docter")
public class Docter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Doct_Id;
    private String Doc_name;
    private String Specialization;
    private String Schedule;
    @Enumerated(EnumType.STRING)
    private Status status;
    
    public Docter(){}
    
    public Docter(String Doc_name,String Specialization,String Schedule,Status status){
        this.Doc_name = Doc_name;
        this.Specialization =Specialization;
        this.Schedule = Schedule;
        this.status = status;
    }
    
    public Long getDoct_Id() {
        return Doct_Id;
    }
    public String Doc_name(){
        return Doc_name;
    }
    public String Specialization(){
        return Specialization;
    }
    public String schedule(){
        return Schedule;
    }
    public Status status(){
        return status;
    }
    
}
