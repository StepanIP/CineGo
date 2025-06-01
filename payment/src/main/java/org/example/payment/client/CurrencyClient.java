package org.example.payment.client;

import org.springframework.stereotype.Component;

@Component
public class CurrencyClient {

    public double convert(double amount, String from, String to) {
        // TODO: реалізувати запит до Currency API (типу fixer.io або свій)
        // Поки що мок
        return amount * 0.93; // наприклад, з USD у EUR
    }
}
