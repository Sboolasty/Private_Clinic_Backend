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
        // Create a list of patients for testing
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        patients.add(new Patient());

        // Mock the behavior of the patientService
        when(patientService.findAllPatients()).thenReturn(patients);

        // Perform the GET request to /patients
        List<Patient> result = patientController.findAllPatients();

        // Verify that the patientService's findAllPatients() method was called
        verify(patientService, times(1)).findAllPatients();

        // Verify that the result matches the expected list of patients
        assertEquals(patients, result);
    }

    @Test
    public void testFindPatientById() {
        // Create a patient for testing
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setIdPatient(patientId);

        // Mock the behavior of the patientService
        when(patientService.findPatientById(patientId)).thenReturn(Optional.of(patient));

        // Perform the GET request to /patients/{id}
        Optional<Patient> result = patientController.findPatientById(patientId);

        // Verify that the patientService's findPatientById() method was called
        verify(patientService, times(1)).findPatientById(patientId);

        // Verify that the result matches the expected patient
        assertTrue(result.isPresent());
        assertEquals(patient, result.get());
    }
}