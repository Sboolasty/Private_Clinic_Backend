package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.entity.Patient;
import com.example.private_clinic_backend.service.PatientService;
import com.example.private_clinic_backend.service.PatientServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @Test
    public void testFindAllPatients() {
        // given
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        patients.add(new Patient());

        // when & then
        when(patientService.findAllPatients()).thenReturn(patients);
        List<Patient> result = patientController.findAllPatients();

        verify(patientService, times(1)).findAllPatients();
        assertEquals(patients, result);
    }

    @Test
    public void testFindPatientById() {
        // given
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setIdPatient(patientId);

        // when & then
        when(patientService.findPatientById(patientId)).thenReturn(Optional.of(patient));
        Optional<Patient> result = patientController.findPatientById(patientId);

        verify(patientService, times(1)).findPatientById(patientId);
        assertTrue(result.isPresent());
        assertEquals(patient, result.get());
    }
}