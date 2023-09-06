package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.entity.Rate;
import com.example.private_clinic_backend.repository.RateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyRateServiceTest {

    private CurrencyRateService currencyRateService;
    private RateRepository rateRepository;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        rateRepository = mock(RateRepository.class);
        restTemplate = mock(RestTemplate.class);
        objectMapper = mock(ObjectMapper.class);
        currencyRateService = new CurrencyRateService(restTemplate, objectMapper);
        currencyRateService.rateRepository = rateRepository;
    }

    @Test
    void testFetchDataFromExternalAPI_Success() throws Exception {
        // given
        String mockApiResponse = "[{\"code\":\"USD\",\"rate\":4.15},{\"code\":\"EUR\",\"rate\":4.5},{\"code\":\"CHF\",\"rate\":4.68}]";
        Rate[] mockRates = {
                new Rate("USD", "USD", 4.15, 1L),
                new Rate("EUR", "EUR", 4.5, 2L),
                new Rate("CHF", "CHF", 4.68, 3L)
        };

        // when&then
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockApiResponse);
        when(objectMapper.readValue(anyString(), eq(Rate[].class))).thenReturn(mockRates);

        currencyRateService.fetchDataFromExternalAPI();

        verify(rateRepository, times(3)).save(any(Rate.class));
    }
    @Test
    void testGetAllRates() {
        // given
        List<Rate> mockRates = Arrays.asList(
                new Rate("USD", "USD", 4.15, 1L),
                new Rate("EUR", "EUR", 4.5, 2L),
                new Rate("CHF", "CHF", 4.68, 3L)
        );

        // when&then
        when(rateRepository.findAll()).thenReturn(mockRates);

        List<Rate> result = currencyRateService.getAllRates();

        assertEquals(mockRates, result);
    }

    @Test
    void testGetRate() {
        // given
        Rate mockRate = new Rate("USD", "USD", 4.15, 1L);

        // when&then
        when(rateRepository.findRateByCode("USD")).thenReturn(mockRate);

        Rate result = currencyRateService.getRate("USD");

        assertEquals(mockRate, result);
    }
}
