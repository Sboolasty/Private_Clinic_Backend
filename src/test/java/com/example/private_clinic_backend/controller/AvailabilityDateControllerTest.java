package com.example.private_clinic_backend.controller;
import com.example.private_clinic_backend.dto.AvailabiltyDateDto;
import com.example.private_clinic_backend.entity.AvailabilityDate;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.service.AvailabilityDateService;
import com.example.private_clinic_backend.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;



public class AvailabilityDateControllerTest {

    @InjectMocks
    private AvailabilityDateController availabilityDateController;

    @Mock
    private AvailabilityDateService availabilityDateService;

    @Mock
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testFindAvailabilityDatesByDoctor() {
        Long doctorId = 1L;
        List<AvailabilityDate> availabilityDates = new ArrayList<>();
        when(availabilityDateService.findAvailabilityDateByDoctor(doctorId)).thenReturn(availabilityDates);

        ResponseEntity<List<AvailabilityDate>> response = availabilityDateController.findAvailabilityDatesByDoctor(doctorId);

        verify(availabilityDateService, times(1)).findAvailabilityDateByDoctor(doctorId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() == availabilityDates;
    }

    @Test
    public void testFindAvailabilityDatesByUserId() {
        // given
        String userIdNumber = "123456789";
        Doctor doctor = new Doctor();
        doctor.setAvailabilityDates(new ArrayList<>());

        // when & then
        when(doctorService.getDoctorByUserIdNumber(userIdNumber)).thenReturn(doctor);

        ResponseEntity<List<AvailabilityDate>> response = availabilityDateController.findAvailabilityDatesByUserId(userIdNumber);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size()); // Zakładając, że lekarz nie ma dostępnych dat
    }





    @Test
    public void testAddAvailabilityDateToDoctor() {
        AvailabiltyDateDto availabiltyDateDto = new AvailabiltyDateDto();
        availabiltyDateDto.setDate(LocalDateTime.now());
        when(availabilityDateService.addAvailabilityDate(availabiltyDateDto)).thenReturn(availabiltyDateDto);

        ResponseEntity<AvailabiltyDateDto> response = availabilityDateController.addAvailabilityDateToDoctor(availabiltyDateDto);

        verify(availabilityDateService, times(1)).addAvailabilityDate(availabiltyDateDto);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() == availabiltyDateDto;
    }
}
