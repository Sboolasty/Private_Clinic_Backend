package com.example.private_clinic_backend.controller;


import com.example.private_clinic_backend.dto.ReserveAppointmentDto;
import com.example.private_clinic_backend.dto.ResultAppointmentDto;
import com.example.private_clinic_backend.entity.Appointment;
import com.example.private_clinic_backend.service.AppointmentService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 3600)
@Data
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/appointment")
    public ResultAppointmentDto createAppointment(@RequestBody ReserveAppointmentDto reserveAppointmentDto){
        return appointmentService.reserveAppointment(reserveAppointmentDto);
    }

    @PatchMapping("/appointment/{appointmentId}")
    public ResponseEntity<ResultAppointmentDto> updateAppointment(@PathVariable Long appointmentId, @RequestBody ReserveAppointmentDto reserveAppointmentDto){
        ResultAppointmentDto resultAppointmentDto =  appointmentService.updateAppointment(appointmentId, reserveAppointmentDto);

        return ResponseEntity.ok(resultAppointmentDto);
    }

    @DeleteMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId){
        Boolean result =  appointmentService.deleteAppointment(appointmentId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> findAllAppointments(){
        List<Appointment> appointmentList = appointmentService.findAllAppointments();

        return ResponseEntity.ok(appointmentList);
    }




}
