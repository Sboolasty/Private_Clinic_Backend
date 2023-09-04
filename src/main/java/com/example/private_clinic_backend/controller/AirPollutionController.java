package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.dto.PollutionDto;
import com.example.private_clinic_backend.entity.AirPollution;
import com.example.private_clinic_backend.entity.Currency;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.entity.Rate;
import com.example.private_clinic_backend.service.AirPollutionService;
import com.example.private_clinic_backend.service.CurrencyRateService;
import com.example.private_clinic_backend.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
