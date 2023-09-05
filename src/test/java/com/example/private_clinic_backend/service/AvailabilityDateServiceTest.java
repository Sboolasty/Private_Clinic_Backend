package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.dto.AvailabiltyDateDto;
import com.example.private_clinic_backend.dto.DoctorDto;
import com.example.private_clinic_backend.entity.AvailabilityDate;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.repository.AvailabilityDateRepository;
import com.example.private_clinic_backend.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AvailabilityDateServiceTest {

    @InjectMocks
    private AvailabilityDateService availabilityDateService;

    @Mock
    private AvailabilityDateRepository availabilityDateRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAvailabilityDate() {
        // Przygotowanie danych testowych
        AvailabilityDate availabilityDate = new AvailabilityDate(
                LocalDateTime.now(),
                true,
                30,
                new Doctor()
        );

        // Kiedy wywołujemy availabilityDateRepository.save, zwracamy ten sam obiekt, który został przekazany do metody
        when(availabilityDateRepository.save(any(AvailabilityDate.class))).thenReturn(availabilityDate);

        // Wywołanie metody, którą testujemy
        AvailabilityDate result = availabilityDateService.createAvailabilityDate(availabilityDate);

        // Sprawdzenie, czy metoda zwraca poprawny wynik
        assertNotNull(result);
        assertEquals(availabilityDate, result);

        // Upewnienie się, że availabilityDateRepository.save zostało wywołane raz
        verify(availabilityDateRepository, times(1)).save(any(AvailabilityDate.class));
    }


    @Test
    public void testAddAvailabilityDate() {
        // Tworzenie przykładowych danych
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setLicenseNumber("12345");

        AvailabiltyDateDto availabiltyDateDto = new AvailabiltyDateDto();
        availabiltyDateDto.setDoctorDto(doctorDto);
        availabiltyDateDto.setDate(LocalDateTime.now());
        availabiltyDateDto.setDurationMinutes(30);

        Doctor doctor = new Doctor();
        doctor.setLicenseNumber("12345");

        AvailabilityDate availabilityDate = new AvailabilityDate();
        availabilityDate.setDate(availabiltyDateDto.getDate());
        availabilityDate.setDoctor(doctor);

        // Określenie zachowania atrap
        when(doctorRepository.findDoctorByLicenseNumber(doctorDto.getLicenseNumber())).thenReturn(doctor);
        when(availabilityDateRepository.save(any(AvailabilityDate.class))).thenReturn(availabilityDate);

        // Wywołanie metody testowanej
        AvailabiltyDateDto result = availabilityDateService.addAvailabilityDate(availabiltyDateDto);

        // Aserty
        assertNotNull(result);
        assertEquals(availabiltyDateDto, result);

        // Sprawdzamy, czy metoda save w repository została wywołana
        verify(availabilityDateRepository, times(1)).save(any(AvailabilityDate.class));
    }


    @Test
    public void testFindAvailabilityDateByDoctor() {
        // Przygotowanie danych testowych
        Long idDoctor = 1L;

        Doctor doctor = new Doctor();
        doctor.setIdDoctor(idDoctor);
        List<AvailabilityDate> availabilityDates = new ArrayList<>();
        availabilityDates.add(new AvailabilityDate());
        availabilityDates.add(new AvailabilityDate());
        doctor.setAvailabilityDates(availabilityDates);

        // Kiedy wywołujemy doctorRepository.findById, zwracamy przygotowanego lekarza
        when(doctorRepository.findById(eq(idDoctor))).thenReturn(Optional.of(doctor));

        // Wywołanie metody, którą testujemy
        List<AvailabilityDate> result = availabilityDateService.findAvailabilityDateByDoctor(idDoctor);

        // Sprawdzenie, czy metoda zwraca poprawny wynik
        assertNotNull(result);
        assertEquals(availabilityDates.size(), result.size());

        // Upewnienie się, że doctorRepository.findById zostało wywołane raz
        verify(doctorRepository, times(1)).findById(eq(idDoctor));
    }
}
