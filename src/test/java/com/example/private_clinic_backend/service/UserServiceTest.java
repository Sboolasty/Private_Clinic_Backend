package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.dto.*;
import com.example.private_clinic_backend.entity.*;
import com.example.private_clinic_backend.repository.DoctorRepository;
import com.example.private_clinic_backend.repository.PatientRepository;
import com.example.private_clinic_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AvailabilityDateService availabilityDateService;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, doctorRepository, patientRepository, availabilityDateService);
    }

    @Test
    public void testRegistrationForPatient() {
        RegistrationDto registrationDto = createPatientRegistrationDto();

        when(userRepository.findUserByEmail(registrationDto.getEmail())).thenReturn(null);
        when(patientRepository.findPatientByUserIdNumber(registrationDto.getIdNumber())).thenReturn(null);

        ResultRegistrationDto result = userService.registration(registrationDto);

        assertNotNull(result);
        assertEquals(registrationDto.getEmail(), result.getEmail());
        assertEquals(registrationDto.getPassword(), result.getPassword());
        assertEquals(registrationDto.getFirstName(), result.getFirstName());
        assertEquals(registrationDto.getLastName(), result.getLastName());
        assertEquals(registrationDto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(registrationDto.getAddress(), result.getAddress());

        verify(userRepository, times(1)).save(any(User.class));
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testRegistrationForDoctor() {
        RegistrationDto registrationDto = createDoctorRegistrationDto();

        when(userRepository.findUserByEmail(registrationDto.getEmail())).thenReturn(null);
        when(doctorRepository.findDoctorByLicenseNumber(registrationDto.getDoctorDto().getLicenseNumber())).thenReturn(null);

        when(availabilityDateService.createAvailabilityDate(any(AvailabilityDate.class))).thenReturn(new AvailabilityDate());

        ResultRegistrationDto result = userService.registration(registrationDto);

        assertNotNull(result);
        assertEquals(registrationDto.getEmail(), result.getEmail());
        assertEquals(registrationDto.getPassword(), result.getPassword());
        assertEquals(registrationDto.getFirstName(), result.getFirstName());
        assertEquals(registrationDto.getLastName(), result.getLastName());
        assertEquals(registrationDto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(registrationDto.getAddress(), result.getAddress());

        verify(userRepository, times(1)).save(any(User.class));
        verify(doctorRepository, times(1)).save(any(Doctor.class));

        verify(availabilityDateService, times(3)).createAvailabilityDate(any(AvailabilityDate.class));
    }


    @Test
    public void testLoginUser() {
        UserLoginDto loginForm = new UserLoginDto();
        User user = createUser();


        loginForm.setPassword("twoje_prawidlowe_haslo");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(loginForm.getPassword());
        user.setPassword(encodedPassword);

        when(userRepository.findUserByEmail(loginForm.getEmail())).thenReturn(user);

        ResultUserDto result = userService.loginUser(loginForm);

        assertNotNull(result);
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getIdNumber(), result.getIdNumber());
    }

    private RegistrationDto createPatientRegistrationDto() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setIdNumber("1234567890");
        registrationDto.setEmail("patient@example.com");
        registrationDto.setPassword("password");
        registrationDto.setFirstName("John");
        registrationDto.setLastName("Doe");
        registrationDto.setPhoneNumber("123456789");
        registrationDto.setAddress("123 Street");
        registrationDto.setPatientDto(new PatientDto("1234567890", 70.0, 175.0));
        return registrationDto;
    }

    private RegistrationDto createDoctorRegistrationDto() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setIdNumber("1234567890");
        registrationDto.setEmail("doctor@example.com");
        registrationDto.setPassword("password");
        registrationDto.setFirstName("Dr. Jane");
        registrationDto.setLastName("Smith");
        registrationDto.setPhoneNumber("987654321");
        registrationDto.setAddress("456 Avenue");
        registrationDto.setDoctorDto(new DoctorDto());
        return registrationDto;
    }

      private User createUser() {
        User user = new User();
        user.setIdNumber("1234567890");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoneNumber("123456789");
        user.setAddress("123 Street");
        user.setRole(UserRole.PATIENT);
        return user;
    }
}
