package com.example.private_clinic_backend.service;

import com.example.private_clinic_backend.entity.Rate;
import com.example.private_clinic_backend.repository.RateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;
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
        // Przygotowanie danych testowych
        String mockApiResponse = "[{\"code\":\"USD\",\"rate\":4.15},{\"code\":\"EUR\",\"rate\":4.5},{\"code\":\"CHF\",\"rate\":4.68}]";
        Rate[] mockRates = {
                new Rate("USD", "USD", 4.15, 1L),
                new Rate("EUR", "EUR", 4.5, 2L),
                new Rate("CHF", "CHF", 4.68, 3L)
        };

        // Symulowanie zachowań
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockApiResponse);
        when(objectMapper.readValue(anyString(), eq(Rate[].class))).thenReturn(mockRates);

        // Wywołanie metody do testowania
        currencyRateService.fetchDataFromExternalAPI();

        // Sprawdzenie, czy dane zostały zapisane do repozytorium
        verify(rateRepository, times(3)).save(any(Rate.class));
    }
    @Test
    void testGetAllRates() {
        // Przygotowanie danych testowych
        List<Rate> mockRates = Arrays.asList(
                new Rate("USD", "USD", 4.15, 1L),
                new Rate("EUR", "EUR", 4.5, 2L),
                new Rate("CHF", "CHF", 4.68, 3L)
        );

        // Symulowanie zachowania repozytorium
        when(rateRepository.findAll()).thenReturn(mockRates);

        // Wywołanie metody do testowania
        List<Rate> result = currencyRateService.getAllRates();

        // Sprawdzenie, czy metoda zwraca oczekiwane dane
        assertEquals(mockRates, result);
    }

    @Test
    void testGetRate() {
        // Przygotowanie danych testowych
        Rate mockRate = new Rate("USD", "USD", 4.15, 1L);

        // Symulowanie zachowania repozytorium
        when(rateRepository.findRateByCode("USD")).thenReturn(mockRate);

        // Wywołanie metody do testowania
        Rate result = currencyRateService.getRate("USD");

        // Sprawdzenie, czy metoda zwraca oczekiwany wynik
        assertEquals(mockRate, result);
    }
}
