package com.lucasilva.pedidoapp.config;

import com.lucasilva.pedidoapp.services.EmailService;
import com.lucasilva.pedidoapp.services.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}