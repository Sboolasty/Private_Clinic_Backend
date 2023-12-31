package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.dto.AvailabiltyDateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.private_clinic_backend.entity.AvailabilityDate;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.repository.AvailabilityDateRepository;
import com.example.private_clinic_backend.repository.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityDateService {
    @Autowired
    private AvailabilityDateRepository availabilityDateRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public AvailabilityDate createAvailabilityDate(AvailabilityDate availabilityDate){

        Doctor doctor = availabilityDate.getDoctor();
        availabilityDateRepository.save(availabilityDate);

        return availabilityDate;
    }

    public AvailabiltyDateDto addAvailabilityDate(AvailabiltyDateDto availabiltyDateDto) {
        Doctor doctor = doctorRepository.findDoctorByLicenseNumber(availabiltyDateDto.getDoctorDto().getLicenseNumber());

        if (doctor != null) {
            List<AvailabilityDate> availabilityDates = doctor.getAvailabilityDates();

            // Verify that list is null
            if (availabilityDates == null) {
                availabilityDates = new ArrayList<>();
                doctor.setAvailabilityDates(availabilityDates);
            }

            Optional<AvailabilityDate> adOptional = availabilityDates.stream().filter(a -> a.getDate().isEqual(availabiltyDateDto.getDate())).findFirst();

            if (adOptional.isEmpty()) {
                AvailabilityDate availabilityDate = new AvailabilityDate(availabiltyDateDto.getDate(), true, 30, doctor);
                availabilityDateRepository.save(availabilityDate);
                return availabiltyDateDto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<AvailabilityDate> findAvailabilityDateByDoctor(Long idDoctor){

        Optional<Doctor> doctorOptional = doctorRepository.findById(idDoctor);

        return doctorOptional.map(Doctor::getAvailabilityDates).orElse(null);

    }
}

