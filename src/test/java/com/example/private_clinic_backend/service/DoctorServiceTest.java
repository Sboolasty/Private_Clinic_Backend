package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.dto.ScheduleAppointmentDto;
import com.example.private_clinic_backend.entity.Appointment;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.entity.User;
import com.example.private_clinic_backend.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.findAllDoctors();

        assertEquals(1, result.size());
    }

    @Test
    public void testFindDoctorsAppointments() {
        String licenseNumber = "12345";
        Doctor doctor = new Doctor();
        doctor.setLicenseNumber(licenseNumber);

        when(doctorRepository.findDoctorByLicenseNumber(licenseNumber)).thenReturn(doctor);

        List<Appointment> appointments = new ArrayList<>();

        User user = new User();
        user.setAppointmentList(appointments);
        doctor.setUser(user);

        List<ScheduleAppointmentDto> result = doctorService.findDoctorsAppointments(licenseNumber);

        assertNotNull(result);
    }

    @Test
    public void testGetLicenseNumberByUserIdNumber() {
        String userIdNumber = "1234567890";
        Doctor doctor = new Doctor();
        doctor.setLicenseNumber("ABCDE");

        when(doctorRepository.findDoctorByUserIdNumber(userIdNumber)).thenReturn(doctor);

        String result = doctorService.getLicenseNumberByUserIdNumber(userIdNumber);

        assertEquals("ABCDE", result);
    }

    @Test
    public void testGetDoctorByUserIdNumber() {
        String userIdNumber = "1234567890";
        Doctor doctor = new Doctor();

        when(doctorRepository.findDoctorByUserIdNumber(userIdNumber)).thenReturn(doctor);

        Doctor result = doctorService.getDoctorByUserIdNumber(userIdNumber);

        assertNotNull(result);
    }
}