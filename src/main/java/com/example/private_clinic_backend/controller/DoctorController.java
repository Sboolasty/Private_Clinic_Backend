package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.dto.ScheduleAppointmentDto;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.service.DoctorService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 3600)
@Data
@RestController
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> findAllDoctors(){
        List<Doctor> doctors = doctorService.findAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/doctors/{licenseNumber}/appointments")
    public List<ScheduleAppointmentDto> findDoctorsAppointments(@PathVariable(value = "licenseNumber") String licenseNumber){
        return doctorService.findDoctorsAppointments(licenseNumber);
    }

    @GetMapping("/licenseNumberByUser/{userIdNumber}")
    public String getLicenseNumberByUser(@PathVariable String userIdNumber){
        return doctorService.getLicenseNumberByUserIdNumber(userIdNumber);
    }
}

