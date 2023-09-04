package com.example.private_clinic_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ScheduleAppointmentDto {
    private Long appointmentId;

    private LocalDateTime date;

    private String patientFirstName;

    private String patientLastName;

    private String description;

    private String doctorFirstName;

    private String doctorLastName;

}
