package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.dto.PollutionDto;
import com.example.private_clinic_backend.service.AirPollutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;

public class AirPollutionControllerTest {

    private AirPollutionController airPollutionController;

    @Mock
    private AirPollutionService airPollutionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        airPollutionController = new AirPollutionController(airPollutionService);
    }

    @Test
    public void testGetAirPollution() {
        // Create a sample PollutionDto
        PollutionDto mockPollutionDto = new PollutionDto();
        // Mock the behavior of the AirPollutionService
        when(airPollutionService.getPollutionRates()).thenReturn(mockPollutionDto);

        // Call the controller method
        ResponseEntity<PollutionDto> response = airPollutionController.getAirPollution();

        // Verify that the service method was called
        verify(airPollutionService, times(1)).getPollutionRates();

        // Check the response status code
        assert(response.getStatusCode() == HttpStatus.OK);

        // Check that the returned PollutionDto matches the mocked one
        assert(response.getBody() == mockPollutionDto);
    }
}