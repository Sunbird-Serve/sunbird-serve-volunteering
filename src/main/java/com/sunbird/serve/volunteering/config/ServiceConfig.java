package com.sunbird.serve.volunteering.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ServiceConfig {

    @Bean(name = "rcClient")
    public WebClient rcClient() throws IOException {
        return WebClient.builder()
                .baseUrl("http://13.235.243.155:8081/api/v1/")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap(true)
                ))
                .build();
    }

    @Value("${firebase.credentials}")
    private String credentials;

    @Bean
    FirebaseApp firebaseApp() throws IOException {

        InputStream service_account = new ByteArrayInputStream(credentials.getBytes());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(service_account))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
