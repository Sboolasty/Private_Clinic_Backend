package com.example.private_clinic_backend.dto;

import lombok.*;

@Setter
@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor
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
