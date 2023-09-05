package com.example.private_clinic_backend.dto;

import com.example.private_clinic_backend.service.DoctorService;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AvailabiltyDateDto {

    private DoctorDto doctorDto;

    private LocalDateTime date;

    private int durationMinutes;
}
