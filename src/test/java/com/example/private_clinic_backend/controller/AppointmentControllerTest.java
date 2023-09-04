package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.dto.ReserveAppointmentDto;
import com.example.private_clinic_backend.dto.ResultAppointmentDto;
import com.example.private_clinic_backend.entity.Appointment;
import com.example.private_clinic_backend.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    public void testCreateAppointment() throws Exception {
        ReserveAppointmentDto reserveAppointmentDto = new ReserveAppointmentDto();
        // Ustaw dane w obiekcie reserveAppointmentDto

        // Utwórz przykładowy obiekt ResultAppointmentDto
        ResultAppointmentDto resultAppointmentDto = new ResultAppointmentDto();
        // Ustaw dane w obiekcie resultAppointmentDto

        // Mockowanie metody reserveAppointment w serwisie
        when(appointmentService.reserveAppointment(any(ReserveAppointmentDto.class))).thenReturn(resultAppointmentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reserveAppointmentDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(resultAppointmentDto.getDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorLicenseNumber").value(resultAppointmentDto.getDoctorLicenseNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientIdNumber").value(resultAppointmentDto.getPatientIdNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(resultAppointmentDto.getDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateAppointment() throws Exception {
        Long appointmentId = 1L;
        ReserveAppointmentDto reserveAppointmentDto = new ReserveAppointmentDto();
        // Ustaw dane w obiekcie reserveAppointmentDto

        // Utwórz przykładowy obiekt ResultAppointmentDto
        ResultAppointmentDto resultAppointmentDto = new ResultAppointmentDto();
        // Ustaw dane w obiekcie resultAppointmentDto

        // Mockowanie metody updateAppointment w serwisie
        when(appointmentService.updateAppointment(appointmentId, reserveAppointmentDto)).thenReturn(resultAppointmentDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/appointment/{appointmentId}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(reserveAppointmentDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(resultAppointmentDto.getDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorLicenseNumber").value(resultAppointmentDto.getDoctorLicenseNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientIdNumber").value(resultAppointmentDto.getPatientIdNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(resultAppointmentDto.getDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        Long appointmentId = 1L;
        // Mockowanie metody deleteAppointment w serwisie
        when(appointmentService.deleteAppointment(appointmentId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/appointment/{appointmentId}", appointmentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testFindAllAppointments() throws Exception {
        List<Appointment> appointmentList = Arrays.asList(
                new Appointment(),
                new Appointment()
        );

        // Mockowanie metody findAllAppointments w serwisie
        when(appointmentService.findAllAppointments()).thenReturn(appointmentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/appointments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].appointmentId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].appointmentId").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    // Metoda pomocnicza do konwersji obiektów na JSON
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
