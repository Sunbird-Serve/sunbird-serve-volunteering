package com.sunbird.serve.volunteering.config;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class ServiceConfig {

    @Value("${rc.service.base-url}")
    private String rcServiceBaseUrl;

    @Bean(name = "rcClient")
    public WebClient rcClient() throws IOException {
        // Connection pool: max 20 connections, pending queue max 100
        ConnectionProvider provider = ConnectionProvider.builder("rc-pool")
                .maxConnections(20)
                .pendingAcquireMaxCount(100)
                .pendingAcquireTimeout(Duration.ofSeconds(30))
                .build();

        HttpClient httpClient = HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .responseTimeout(Duration.ofSeconds(60))
                .wiretap(true);

        return WebClient.builder()
                .baseUrl(rcServiceBaseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
