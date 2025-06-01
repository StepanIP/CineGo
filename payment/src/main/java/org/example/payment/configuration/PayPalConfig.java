package org.example.payment.configuration;

import com.paypal.base.rest.APIContext;
import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext() {
        return new APIContext(clientId, clientSecret, mode);
    }

    @Bean
    @ConfigurationProperties(prefix = "paypal.redirect-url")
    public RedirectUrl redirectUrl() {
        return new RedirectUrl();
    }

    @Data
    public static class RedirectUrl {

        private String success;

        private String cancel;

        private String error;
    }
}
