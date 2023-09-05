package com.example.private_clinic_backend.controller;

import com.example.private_clinic_backend.entity.Rate;
import com.example.private_clinic_backend.service.CurrencyRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

class CurrencyRateControllerTest {

    @Mock
    private CurrencyRateService currencyRateService;

    @InjectMocks
    private CurrencyRateController currencyRateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurrencyRates_shouldReturnCurrencyRates() {
        // Arrange
        List<Rate> rateList = new ArrayList<>();
        rateList.add(new Rate("USD", "123", 1.0, 1L));
        rateList.add(new Rate("EUR", "456", 2.0, 2L));
        when(currencyRateService.getAllRates()).thenReturn(rateList);

        // Act
        ResponseEntity<List<Rate>> responseEntity = currencyRateController.getCurrencyRates();

        // Assert
        verify(currencyRateService, times(1)).getAllRates();
        assert(responseEntity.getStatusCode().is2xxSuccessful());
        assert(responseEntity.getBody() != null);
        assert(responseEntity.getBody().size() == 2);
    }


    @Test
    void getCurrencyRate_shouldReturnCurrencyRate() {
        // Arrange
        String currencyCode = "USD";
        Rate rate = new Rate("123", "USD", 1.0, 1L);
        when(currencyRateService.getRate(currencyCode)).thenReturn(rate);

        // Act
        ResponseEntity<Rate> responseEntity = currencyRateController.getCurrencyRate(currencyCode);

        // Assert
        verify(currencyRateService, times(1)).getRate(currencyCode);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(currencyCode, responseEntity.getBody().getCode());
    }
}

