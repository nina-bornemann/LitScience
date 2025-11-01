package com.ninabornemann.backend.service;

import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.model.OpenAiMessage;
import com.ninabornemann.backend.model.OpenAiRequest;
import com.ninabornemann.backend.model.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OpenAiService {

    private final PaperRepo paperRepo;
    private final RestClient restClient;

    public OpenAiService(PaperRepo paperRepo, RestClient.Builder restClientBuilder, @Value("${OPENAI_API_KEY}") String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        this.paperRepo = paperRepo;
    }

    public String createReport(@RequestBody String title) {
        OpenAiResponse response = restClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new OpenAiRequest("gpt-3.5-turbo", List.of(
                        new OpenAiMessage("user",
                                """
                                        Please generate a report about the paper with the given title. It should be nicely styled with some fun emojis when fitting.
                                        Summarize the abstract in 2 sentences. Highlight most important scientific methods with metrics of most important reagents,
                                        key findings and a paragraph about the discussion, relevancy and limitations.
                                        If there are any please state 5 papers similar to this study and include their doi and title."""),
                        new OpenAiMessage("user", title))))
                .retrieve()
                .body(OpenAiResponse.class);
        return response.choices().getFirst().message().content();
    }

}
