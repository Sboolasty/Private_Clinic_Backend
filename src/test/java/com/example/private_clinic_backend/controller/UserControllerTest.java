package com.example.private_clinic_backend.controller;


import com.example.private_clinic_backend.dto.RegistrationDto;
import com.example.private_clinic_backend.dto.UserLoginDto;
import com.example.private_clinic_backend.dto.ResultRegistrationDto;
import com.example.private_clinic_backend.dto.ResultUserDto;
import com.example.private_clinic_backend.entity.User;
import com.example.private_clinic_backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegistration() {
        // Przygotowanie danych wejściowych dla testu
        RegistrationDto registrationDto = new RegistrationDto(
                "ID123",
                "example@email.com",
                "password",
                "John",
                "Doe",
                "1234567890",
                "123 Street",
                null, // Możesz pozostawić null lub utworzyć obiekt DoctorDto lub PatientDto
                null  // w zależności od potrzeb
        );

        // Przygotowanie oczekiwanych wyników
        ResultRegistrationDto expectedResult = new ResultRegistrationDto(
                "example@email.com",
                "password",
                "John",
                "Doe",
                "1234567890",
                "123 Street"
        );

        // Konfiguracja zachowań mocka
        when(userService.registration(registrationDto)).thenReturn(expectedResult);

        // Wywołanie metody kontrolera
        ResultRegistrationDto actualResult = userController.registration(registrationDto);

        // Porównanie wyników
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testLoginUser() {
        // Przygotowanie danych wejściowych dla testu
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail("example@email.com");
        loginDto.setPassword("password");

        // Przygotowanie oczekiwanych wyników
        ResultUserDto expectedResult = new ResultUserDto();
        // Uzupełnij oczekiwane wyniki

        // Konfiguracja zachowań mocka
        when(userService.loginUser(loginDto)).thenReturn(expectedResult);

        // Wywołanie metody kontrolera
        ResultUserDto actualResult = userController.loginUser(loginDto);

        // Porównanie wyników
        assertEquals(expectedResult, actualResult);
    }

    // Dodaj więcej testów dla innych metod kontrolera
}