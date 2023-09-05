package com.example.private_clinic_backend.service;
import com.example.private_clinic_backend.dto.DoctorDto;
import com.example.private_clinic_backend.dto.PatientDto;
import com.example.private_clinic_backend.dto.ReserveAppointmentDto;
import com.example.private_clinic_backend.dto.ResultAppointmentDto;
import com.example.private_clinic_backend.entity.Appointment;
import com.example.private_clinic_backend.entity.AvailabilityDate;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.entity.Patient;
import com.example.private_clinic_backend.repository.AppointmentRepository;
import com.example.private_clinic_backend.repository.AvailabilityDateRepository;
import com.example.private_clinic_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AvailabilityDateRepository availabilityDateRepository;

    @Mock
    private UserService userService;

    private AppointmentService appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        appointmentService = new AppointmentService();
        appointmentService.setAppointmentRepository(appointmentRepository);
        appointmentService.setUserRepository(userRepository);
        appointmentService.setAvailabilityDateRepository(availabilityDateRepository);
        appointmentService.setUserService(userService);
    }

    @Test
    public void testReserveAppointment_Failure() {
        ReserveAppointmentDto reserveAppointmentDto = new ReserveAppointmentDto();
        // Ustaw odpowiednie wartości w reserveAppointmentDto

        // Tworzenie przykładowych danych do zwrócenia przez userService.findUser
        Object[] users = {null, null};
        when(userService.findUser(any(), any())).thenReturn(users);

        ResultAppointmentDto result = appointmentService.reserveAppointment(reserveAppointmentDto);

        assertNotNull(result);
        // Dodaj asercje, aby sprawdzić, czy wynik jest zgodny z oczekiwaniami dla przypadku niepowodzenia
    }

}
