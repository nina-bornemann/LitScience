package com.ninabornemann.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ApiService {

    private final RestClient restClient;

    public ApiService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .build();
    }

    public RestClient getRestClient() {
        return restClient;
    }
}
