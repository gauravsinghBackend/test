package com.example.LoginPage.OneTimePassword.OTPconfig;

import com.plivo.api.Plivo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfig {

//    @Value("${plivo.auth.id}")
//    private String authId;
    @Value("${twilio.AccountSid}")
    private String authId;

//    @Value("${plivo.auth.token}")
//    private String authToken;
    @Value("${twilio.AuthToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String phoneNumber;

    @Bean
    public Plivo plivo() {
        return new Plivo();
    }
}

//#Messaging Account AUTHENTIFICATION(Twilio):
//        twilio.AccountSid=AC981fd179c5f63b7d9817cf01f0f1c51f
//        twilio.AuthToken=fe2957a781c65e504ddd7d46fb669e17
//        twilio.phoneNumber=+12564744073
