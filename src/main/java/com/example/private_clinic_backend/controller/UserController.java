package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.dto.*;
import com.example.private_clinic_backend.entity.Appointment;
import com.example.private_clinic_backend.entity.Patient;
import com.example.private_clinic_backend.entity.User;
import com.example.private_clinic_backend.service.UserService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 3600)
@RestController
@Data
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResultRegistrationDto registration (@RequestBody RegistrationDto registrationDto){
        return userService.registration(registrationDto);
    }

    @PostMapping("/login")
    public ResultUserDto loginUser(@RequestBody UserLoginDto loginDto){
        return userService.loginUser(loginDto);
    }

    @GetMapping("/users")
    public List<User> findAllUsers(){
        return userService.findALlUsers();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable(value = "id") Long id){
        return userService.findUserById(id);
    }

    @GetMapping("/user/{idNumber}/appointments")
    public List<Appointment> findUserAppointments(@PathVariable(value = "idNumber") String idNumber){
        return userService.findUserAppointments(idNumber);
    }
}

