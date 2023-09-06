package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.dto.PollutionDto;
import com.example.private_clinic_backend.service.AirPollutionService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 3600)
@RestController
@Data
public class AirPollutionController {

    private final AirPollutionService airPollutionService;

    @GetMapping("/pollution")
    public ResponseEntity<PollutionDto> getAirPollution(){
        PollutionDto airPollution = airPollutionService.getPollutionRates();
        return ResponseEntity.ok(airPollution);
    }
}
