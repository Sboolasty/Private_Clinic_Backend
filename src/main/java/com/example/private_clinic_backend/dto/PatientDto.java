package com.example.private_clinic_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientDto {

    private String idNumber;

    private double weight;

    private double height;
}
