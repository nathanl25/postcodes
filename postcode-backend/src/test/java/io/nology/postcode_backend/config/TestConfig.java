package io.nology.postcode_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

@Configuration
public class TestConfig {
    @Bean
    public Faker faker() {
        return new Faker();
    }
}
