package com.currencies.nbpApiClient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ExchangeRateApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateApiClient client;

    private static final String TEST_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(client, "url", TEST_URL);
    }

    @Test
    void fetchCurrencyRatesFromNBP_ShouldReturnValidResponse() {
        String result = client.fetchCurrencyRatesFromNBP();

        Assertions.assertNotNull(result);}
} 