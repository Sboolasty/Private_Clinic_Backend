package com.example.private_clinic_backend.repository;

import com.example.private_clinic_backend.entity.Patient;
import com.example.private_clinic_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findPatientByUser(User user);

    Patient findPatientByUserIdNumber(String idNumber);
}
