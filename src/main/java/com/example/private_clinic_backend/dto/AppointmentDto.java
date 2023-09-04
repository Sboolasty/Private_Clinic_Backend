package com.example.private_clinic_backend.dto;

import com.example.private_clinic_backend.entity.AvailabilityDate;
import com.example.private_clinic_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
@Data
@Getter
@AllArgsConstructor
public class AppointmentDto {

    private AvailabilityDate appointmentDate;

    private String description;

    private User user;
}
