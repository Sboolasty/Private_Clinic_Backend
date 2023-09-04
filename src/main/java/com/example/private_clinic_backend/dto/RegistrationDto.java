package com.example.private_clinic_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
@AllArgsConstructor
@Getter
public class RegistrationDto {
    private String idNumber;


    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private DoctorDto doctorDto;

    private PatientDto patientDto;
}
