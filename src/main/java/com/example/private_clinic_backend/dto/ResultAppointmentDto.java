package com.example.private_clinic_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultAppointmentDto {


    private LocalDateTime date;

    private String doctorLicenseNumber;

    private String patientIdNumber;

    private String description;
}
