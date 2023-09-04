package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.entity.Patient;
import com.example.private_clinic_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAllPatients() {return patientRepository.findAll();}

    public Optional<Patient> findPatientById(Long id) {return patientRepository.findById(id);}

}
