package com.example.private_clinic_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultRegistrationDto {

    private String email;

    private String password;

    private String firstName;


    private String lastName;

    private String phoneNumber;

    private String address;


}
