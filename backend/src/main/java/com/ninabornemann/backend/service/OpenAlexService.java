package com.ninabornemann.backend.service;
import com.ninabornemann.backend.model.OpenAlexResponse;
import com.ninabornemann.backend.model.Paper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;

@Service
public class OpenAlexService {

    private final RestClient restClient;

    public OpenAlexService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.openalex.org")
                .build();
    }

    public OpenAlexResponse getPaperByDoi() {
        return restClient.get().uri("/works/https://doi.org/10.7717/peerj.4375")
                .retrieve()
                .body(OpenAlexResponse.class);
    }
}
