package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.entity.Patient;
import com.example.private_clinic_backend.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.findAllPatients();

        assertEquals(1, result.size());
    }

    @Test
    public void testFindPatientById() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setIdPatient(patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.findPatientById(patientId);

        assertTrue(result.isPresent());
        assertEquals(patientId, result.get().getIdPatient());
    }
}