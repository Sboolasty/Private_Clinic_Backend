package com.example.private_clinic_backend.repository;

import com.example.private_clinic_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findDoctorByUserIdNumber(String idNumber);

    Doctor findDoctorByLicenseNumber(String licenseNumber);
}
