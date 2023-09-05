package com.example.private_clinic_backend.controller;
import com.example.private_clinic_backend.dto.ReserveAppointmentDto;
import com.example.private_clinic_backend.dto.ResultAppointmentDto;
import com.example.private_clinic_backend.entity.Appointment;
import com.example.private_clinic_backend.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    private AppointmentController appointmentController;

    @Mock
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        appointmentController = new AppointmentController(appointmentService);
    }

    @Test
    void testCreateAppointment() {
        // Arrange
        ReserveAppointmentDto reserveAppointmentDto = new ReserveAppointmentDto();
        ResultAppointmentDto expectedResult = new ResultAppointmentDto();
        when(appointmentService.reserveAppointment(reserveAppointmentDto)).thenReturn(expectedResult);

        // Act
        ResultAppointmentDto result = appointmentController.createAppointment(reserveAppointmentDto);

        // Assert
        verify(appointmentService, times(1)).reserveAppointment(reserveAppointmentDto);
        // Add more assertions as needed based on the expected behavior of your controller
    }

    @Test
    void testUpdateAppointment() {
        // Arrange
        Long appointmentId = 1L;
        ReserveAppointmentDto reserveAppointmentDto = new ReserveAppointmentDto();
        ResultAppointmentDto expectedResult = new ResultAppointmentDto();
        when(appointmentService.updateAppointment(appointmentId, reserveAppointmentDto)).thenReturn(expectedResult);

        // Act
        ResponseEntity<ResultAppointmentDto> response = appointmentController.updateAppointment(appointmentId, reserveAppointmentDto);

        // Assert
        verify(appointmentService, times(1)).updateAppointment(appointmentId, reserveAppointmentDto);
        // Add more assertions as needed based on the expected behavior of your controller and ResponseEntity
    }

    @Test
    void testDeleteAppointment() {
        // Arrange
        Long appointmentId = 1L;
        when(appointmentService.deleteAppointment(appointmentId)).thenReturn(true);

        // Act
        ResponseEntity<?> response = appointmentController.deleteAppointment(appointmentId);

        // Assert
        verify(appointmentService, times(1)).deleteAppointment(appointmentId);
        // Add more assertions as needed based on the expected behavior of your controller and ResponseEntity
    }

    @Test
    void testFindAllAppointments() {
        // Arrange
        List<Appointment> appointmentList = new ArrayList<>();
        when(appointmentService.findAllAppointments()).thenReturn(appointmentList);

        // Act
        ResponseEntity<List<Appointment>> response = appointmentController.findAllAppointments();

        // Assert
        verify(appointmentService, times(1)).findAllAppointments();
        // Add more assertions as needed based on the expected behavior of your controller and ResponseEntity
    }
}
