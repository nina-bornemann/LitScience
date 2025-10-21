package com.ninabornemann.backend.service;
import com.ninabornemann.backend.exceptions.DoiNotFoundException;
import com.ninabornemann.backend.model.OpenAlexResponse;
import com.ninabornemann.backend.model.PaperDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.URI;

@Service
public class OpenAlexService {

    private final RestClient restClient;

    public OpenAlexService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.openalex.org")
                .build();
    }

    public PaperDto getPaperByDoi(String doi) throws DoiNotFoundException {
        // because of the double slash in uri
        String absolute = "https://api.openalex.org/works/https://doi.org/" + doi;
        OpenAlexResponse response;
        try {
            response = restClient.get().uri(URI.create(absolute))
                .retrieve()
                .body(OpenAlexResponse.class);
        }
        catch (RestClientException e) {
            throw new DoiNotFoundException("Paper not found.");
        }

        return new PaperDto(
                doi,
                response.title(),
                response.authorships().getFirst().author().displayName(),
                response.publicationYear(),
                "",
                "");
    }
}
