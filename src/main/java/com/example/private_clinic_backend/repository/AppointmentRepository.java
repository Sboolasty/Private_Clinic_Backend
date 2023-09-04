package com.example.private_clinic_backend.repository;

import com.example.private_clinic_backend.entity.Appointment;
import com.example.private_clinic_backend.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAppointmentsByStatus(AppointmentStatus status);

}
