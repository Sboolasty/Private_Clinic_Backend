package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.dto.AvailabiltyDateDto;
import com.example.private_clinic_backend.entity.AvailabilityDate;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.service.AvailabilityDateService;
import com.example.private_clinic_backend.service.DoctorService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 3600)
@Data
@RestController
public class AvailabilityDateController {

    private final AvailabilityDateService availabilityDateService;
    private final DoctorService doctorService;

    @GetMapping("doctors/{id}/availability")
    public ResponseEntity<List<AvailabilityDate>> findAvailabilityDatesByDoctor(
            @PathVariable(value = "id") Long doctorId
    ) {
        List<AvailabilityDate> availabilityDates = availabilityDateService.findAvailabilityDateByDoctor(doctorId);

        return ResponseEntity.ok(availabilityDates);
    }

    @GetMapping("users/{idNumber}/availability")
    public ResponseEntity<List<AvailabilityDate>> findAvailabilityDatesByUserId(
            @PathVariable(value = "idNumber") String idNumber
    ) {
        Doctor doctor = doctorService.getDoctorByUserIdNumber(idNumber);
        List<AvailabilityDate> availabilityDates = doctor.getAvailabilityDates();

        return ResponseEntity.ok(availabilityDates);
    }

    @PostMapping("doctors/addAvailability")
    public ResponseEntity<AvailabiltyDateDto> addAvailabilityDateToDoctor(@RequestBody AvailabiltyDateDto availabiltyDateDto){
        availabilityDateService.addAvailabilityDate(availabiltyDateDto);
        return ResponseEntity.ok(availabiltyDateDto);
    }
}

