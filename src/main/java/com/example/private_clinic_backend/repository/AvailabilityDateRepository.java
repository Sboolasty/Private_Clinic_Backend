package com.example.private_clinic_backend.repository;

import com.example.private_clinic_backend.entity.AvailabilityDate;
import com.example.private_clinic_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityDateRepository extends JpaRepository<AvailabilityDate, Long> {
    List<AvailabilityDate> findAvailabilityDateByDoctor(Doctor doctor);
}
