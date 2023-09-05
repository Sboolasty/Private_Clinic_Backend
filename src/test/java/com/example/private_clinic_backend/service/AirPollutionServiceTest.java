package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.dto.PollutionDto;

import com.example.private_clinic_backend.entity.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AirPollutionServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private AirPollutionService airPollutionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        airPollutionService = new AirPollutionService(restTemplate, objectMapper);
    }

    @Test
    public void testGetPollutionRates() throws Exception {
        // Prepare mock data
        Value value = new Value();
        value.setDate("2023-09-05");
        value.setValue(10.0);

        String apiUrl = "https://api.gios.gov.pl/pjp-api/rest/data/getData/3584";
        String mockResponse = "mocked JSON response";
        PollutionDto mockPollutionDto = new PollutionDto();
        List<Value> mockValues = new ArrayList<>();
        mockValues.add(value); // Add the value to the mockValues list
        mockPollutionDto.setValues(mockValues); // Set the mockValues in mockPollutionDto

        // Mock restTemplate behavior
        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(mockResponse);
        when(objectMapper.readValue(mockResponse, PollutionDto.class)).thenReturn(mockPollutionDto);

        // Call the method to test
        PollutionDto result = airPollutionService.getPollutionRates();

        // Verify that restTemplate and objectMapper methods were called
        verify(restTemplate, times(1)).getForObject(apiUrl, String.class);
        verify(objectMapper, times(1)).readValue(mockResponse, PollutionDto.class);

        // Assert the result
        assertEquals(1, result.getValues().size());
        assertEquals("2023-09-05", result.getValues().get(0).getDate());
        assertEquals(10.0, result.getValues().get(0).getValue(), 0.01);
    }
}
