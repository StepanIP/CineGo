package com.screening.config;

import com.screening.facade.SeatFacade;
import com.screening.service.SeatService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class SeatConfiguration {

    @Bean
    public SeatFacade seatFacade(@Lazy final SeatService seatService) {
        return seatService;
    }
}
