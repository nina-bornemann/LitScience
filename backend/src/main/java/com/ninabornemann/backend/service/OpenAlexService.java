package com.ninabornemann.backend.service;
import com.ninabornemann.backend.model.OpenAlexResponse;
import com.ninabornemann.backend.model.PaperDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.net.URI;

@Service
public class OpenAlexService {

    private final RestClient restClient;

    public OpenAlexService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.openalex.org")
                .build();
    }

    public PaperDto getPaperByDoi(String doi) {
        // because of the double slash in uri
        String absolute = "https://api.openalex.org/works/https://doi.org/" + doi;
        OpenAlexResponse response = restClient.get().uri(URI.create(absolute))
                .retrieve()
                .body(OpenAlexResponse.class);

        if (response == null) {
            throw new RuntimeException("Paper not found.");
        }

        return new PaperDto(
                doi,
                response.title(),
                response.authorships().getFirst().author().display_name(),
                response.publication_year(),
                "",
                "");
    }
}
