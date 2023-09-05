package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.controller.UserController;
import com.example.private_clinic_backend.dto.RegistrationDto;
import com.example.private_clinic_backend.dto.ResultRegistrationDto;
import com.example.private_clinic_backend.dto.ResultUserDto;
import com.example.private_clinic_backend.dto.UserLoginDto;
import com.example.private_clinic_backend.entity.Appointment;
import com.example.private_clinic_backend.entity.User;
import com.example.private_clinic_backend.entity.UserRole;
import com.example.private_clinic_backend.service.UserService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegistration() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto(
                "123456789",
                "test@example.com",
                "password123",
                "John",
                "Doe",
                "1234567890",
                "123 Main St",
                null,
                null
        );

        ResultRegistrationDto result = new ResultRegistrationDto(
                "test@example.com", "password123", "John", "Doe", "1234567890", "123 Main St"
        );

        when(userService.registration(registrationDto)).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testLoginUser() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        when(userService.loginUser(loginDto)).thenReturn(new ResultUserDto(UserRole.PATIENT, "123456789"));

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<User> userList = Arrays.asList(new User(), new User());
        when(userService.findALlUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(userList.size()));
    }


    @Test
    public void testFindUserById() throws Exception {
        // Przykładowy użytkownik o ID 1
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        // Mockowanie metody userService.findUserById, aby zwracała przykładowego użytkownika
        when(userService.findUserById(userId)).thenReturn(user);

        // Wykonanie żądania GET
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId));
    }

    @Test
    public void testFindUserAppointments() throws Exception {
        String idNumber = "123456789";
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(userService.findUserAppointments(idNumber)).thenReturn(appointments);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{idNumber}/appointments", idNumber))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(appointments.size()));
    }
}
