package com.challenge.exchangeratechallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
/**
 * Created By Alekhya Tirumalagiri
 */
@Configuration
@Scope("singleton")
public class ApplicationConfig {

    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
