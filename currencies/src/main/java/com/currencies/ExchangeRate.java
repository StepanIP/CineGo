package com.currencies;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exchange_rate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    private String code;

    private double rate;

    public ExchangeRate(String currency, String code, double rate) {
        this.currency = currency;
        this.code = code;
        this.rate = rate;
    }
}

