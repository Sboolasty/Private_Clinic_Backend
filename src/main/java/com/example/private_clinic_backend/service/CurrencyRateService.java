package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.entity.Currency;
import com.example.private_clinic_backend.entity.Rate;
import com.example.private_clinic_backend.repository.RateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyRateService {

    @Autowired
    public RateRepository rateRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CurrencyRateService(RestTemplate restTemplate, ObjectMapper objectMapper){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchDataFromExternalAPI() {
        String apiUrl = "https://api.nbp.pl/api/exchangerates/tables/a?format=json";


        try {
            String response = restTemplate.getForObject(apiUrl, String.class);

            Currency responseObject = objectMapper.readValue(response.substring(1, response.length() - 1), Currency.class);
            List<Rate> allRates = responseObject.getRateList();
            List<Rate> filteredRates = new ArrayList<>();

            for (Rate rate : allRates
                 ) {
                if (rate.getCode().equals("USD") ){
                    rate.setRateId(1L);
                    filteredRates.add(rate);
                }
                else if (rate.getCode().equals("EUR")){
                    rate.setRateId(2L);
                    filteredRates.add(rate);
                }
                else if (rate.getCode().equals("CHF")){
                    rate.setRateId(3L);
                    filteredRates.add(rate);
                }
            }

            for (Rate rate: filteredRates
                 ) {
                rateRepository.save(rate);
                System.out.println(rate.toString() + " saved");
            }

        } catch (Exception e) {
            // Handle the exception
            Rate rateUSD = new Rate("", "USD", 4.15, 1L);
            Rate rateEUR = new Rate("", "EUR", 4.5, 2L);
            Rate rateCHF = new Rate("", "CHF", 4.68, 3L);

            rateRepository.save(rateUSD);
            rateRepository.save(rateEUR);
            rateRepository.save(rateCHF);
            System.out.println(e.toString());
        }
    }

    public List<Rate> getAllRates(){
        return rateRepository.findAll();
    }

    public Rate getRate(String code) {
        return rateRepository.findRateByCode(code);
    }
}
