package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.dto.ScheduleAppointmentDto;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    public void testFindAllDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        // given
        Doctor doctor1 = new Doctor();
        doctor1.setIdDoctor(1L);
        doctor1.setLicenseNumber("12345");
        doctors.add(doctor1);
        //when& then
        when(doctorService.findAllDoctors()).thenReturn(doctors);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idDoctor").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].licenseNumber").value("12345"));

        verify(doctorService, times(1)).findAllDoctors();
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void testFindDoctorsAppointments() throws Exception {
        String licenseNumber = "12345";
        List<ScheduleAppointmentDto> appointments = new ArrayList<>();
        // given
        ScheduleAppointmentDto appointment1 = new ScheduleAppointmentDto(
                1L,
                LocalDateTime.now(),
                "John",
                "Doe",
                "Regular Checkup",
                "Dr. Jane",
                "Smith"
        );
        appointments.add(appointment1);
        //when & then
        when(doctorService.findDoctorsAppointments(licenseNumber)).thenReturn(appointments);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{licenseNumber}/appointments", licenseNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].appointmentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].patientFirstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].patientLastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Regular Checkup"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].doctorFirstName").value("Dr. Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].doctorLastName").value("Smith"));

        verify(doctorService, times(1)).findDoctorsAppointments(licenseNumber);
        verifyNoMoreInteractions(doctorService);
    }

    @Test
    public void testGetLicenseNumberByUser() throws Exception {
        //given
        String userIdNumber = "user123";
        String licenseNumber = "12345";
        //when & then
        when(doctorService.getLicenseNumberByUserIdNumber(userIdNumber)).thenReturn(licenseNumber);

        mockMvc.perform(MockMvcRequestBuilders.get("/licenseNumberByUser/{userIdNumber}", userIdNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(licenseNumber));

        verify(doctorService, times(1)).getLicenseNumberByUserIdNumber(userIdNumber);
        verifyNoMoreInteractions(doctorService);
    }
}
