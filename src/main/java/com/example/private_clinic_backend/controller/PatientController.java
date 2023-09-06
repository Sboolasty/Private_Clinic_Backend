package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.entity.Patient;
import com.example.private_clinic_backend.service.PatientService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 3600)
@Data
@RestController
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> findAllPatients(){
        return patientService.findAllPatients();
    }

    @GetMapping("/patients/{id}")
    public Optional<Patient> findPatientById(@PathVariable(value = "id") Long id){
        return patientService.findPatientById(id);
    }
}
