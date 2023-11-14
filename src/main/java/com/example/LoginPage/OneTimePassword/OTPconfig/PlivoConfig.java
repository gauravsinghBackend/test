package com.example.LoginPage.OneTimePassword.OTPconfig;

import com.plivo.api.Plivo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class PlivoConfig {

    @Value("${plivo.auth.id}")
    private String authId;

    @Value("${plivo.auth.token}")
    private String authToken;

    @Bean
    public Plivo plivo() {
        return new Plivo();
    }
}
