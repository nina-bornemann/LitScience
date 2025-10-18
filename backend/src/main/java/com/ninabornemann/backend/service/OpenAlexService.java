package com.ninabornemann.backend.service;
import com.ninabornemann.backend.model.OpenAlexResponse;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
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

    public PaperDto getPaperByDoi(String doi) {
        OpenAlexResponse response = restClient.get().uri("/works/https://doi.org/" + doi)
                .retrieve()
                .body(OpenAlexResponse.class);
        return new PaperDto(
                doi,
                response.title(),
                response.authorships().getFirst().author().display_name(),
                response.publication_year(),
                "",
                "");
    }
}
