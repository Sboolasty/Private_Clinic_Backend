package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.entity.Currency;
import com.example.private_clinic_backend.entity.Doctor;
import com.example.private_clinic_backend.entity.Rate;
import com.example.private_clinic_backend.service.CurrencyRateService;
import com.example.private_clinic_backend.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"}, maxAge = 3600)
@RestController
@Data
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    @GetMapping("/currencies")
    public ResponseEntity<List<Rate>> getCurrencyRates(){
        List<Rate> rateList = currencyRateService.getAllRates();
        return ResponseEntity.ok(rateList);
    }

    @GetMapping("/currency/{code}")
    public ResponseEntity<Rate> getCurrencyRate(@PathVariable(value = "code") String code){
        Rate rate = currencyRateService.getRate(code);
        return ResponseEntity.ok(rate);
    }
}
