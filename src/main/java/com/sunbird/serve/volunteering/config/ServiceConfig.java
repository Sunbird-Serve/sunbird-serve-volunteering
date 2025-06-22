package com.sunbird.serve.volunteering.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;
import java.io.IOException;

@Configuration
public class ServiceConfig {

    @Value("${rc.service.base-url}")
    private String rcServiceBaseUrl;

    @Bean(name = "rcClient")
    public WebClient rcClient() throws IOException {
        return WebClient.builder()
                .baseUrl(rcServiceBaseUrl)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap(true)
                ))
                .build();
    }
}
