package com.example.bookstorebe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class PaymentService {

    @Value("${paypal.url}")
    private final String paypalUrl;

    @Value("${paypal.password}")  // (1)
    private String paypalPassword;

    public PaymentService(@Value("${paypal.url}") String paypalUrl) { // (2)
        this.paypalUrl = paypalUrl;
    }
}
