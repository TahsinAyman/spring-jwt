package com.fullstackbd.tahsin.backend.config;

import com.fullstackbd.tahsin.backend.service.EmailVerifierClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class EmailVerifierWebClientConfiguration {

    @Bean
    public WebClient employeeWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
    }

    @Bean
    public EmailVerifierClient employeeClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(employeeWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(EmailVerifierClient.class);
    }
}
