package com.example.private_clinic_backend.repository;

import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate,Long> {
    Rate findRateByCode(String code);

}
