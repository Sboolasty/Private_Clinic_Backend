package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.dto.PollutionDto;
import com.example.private_clinic_backend.entity.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AirPollutionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    @Autowired
    public AirPollutionService(RestTemplate restTemplate, ObjectMapper objectMapper){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

    }

    public PollutionDto getPollutionRates() {
        String apiUrl = "https://api.gios.gov.pl/pjp-api/rest/data/getData/3584";
        String response = restTemplate.getForObject(apiUrl, String.class);

        LocalDate currentDate = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the date using the defined format
        String formattedDate = currentDate.format(formatter);

        try {
            PollutionDto responseObject = objectMapper.readValue(response, PollutionDto.class);

            List<Value> values = responseObject.getValues();
            List<Value> filtered = new ArrayList<>();

            for (Value v : values){
                if (!Double.isNaN(v.getValue()) && v.getDate().contains(formattedDate)){
                    filtered.add(v);
                }
            }

            responseObject.setValues(filtered);

            return responseObject;
        } catch (Exception e) {
            // Handle the exception
            System.out.println(e.toString());

            return null;
        }

    }
}
