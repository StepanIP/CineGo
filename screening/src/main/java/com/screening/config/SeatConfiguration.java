package com.screening.config;

import com.screening.facade.SeatFacade;
import com.screening.seat.SeatService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
class SeatConfiguration {

    @Bean
    public SeatFacade seatFacade(@Lazy final SeatService seatService) {
        return seatService;
    }
}
