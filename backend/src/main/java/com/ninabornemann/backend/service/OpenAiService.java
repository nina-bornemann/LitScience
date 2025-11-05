package com.ninabornemann.backend.service;

import com.ninabornemann.backend.model.OpenAiMessage;
import com.ninabornemann.backend.model.OpenAiRequest;
import com.ninabornemann.backend.model.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class OpenAiService {

    private final ApiService apiService;
    private final String apiKey;

    public OpenAiService(ApiService apiService, @Value("${OPENAI_API_KEY}") String apiKey) {
        this.apiService = apiService;
        this.apiKey = apiKey;
    }

    public String createReport(@RequestBody String title) {
        OpenAiResponse response = apiService.getRestClient().post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " +apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new OpenAiRequest("gpt-4-turbo", List.of(
                        new OpenAiMessage("user",
                                """
                                        Please generate a report about the paper with the given title. It should be nicely styled, implement linebreaks after
                                        paragraphs using \n, with fun emojis fitting each paragraph, BUT DO NOT USE MARKDOWN!. It will show as a string in the end so use html-friendly paragraphs.
                                        Summarize the abstract in 2 sentences. Highlight most important scientific methods with metrics of most important reagents,
                                        key findings and a paragraph about the discussion, relevancy and limitations. Include paragraph headlines for each.
                                        Extract DOIs from the papers reference section that lead to similar studies than the paper, no guesses, no invented items.NEVER MAKE UP FAKE DOIs please."""),
                        new OpenAiMessage("user", title))))
                .retrieve()
                .body(OpenAiResponse.class);
        return response.choices().getFirst().message().content();
    }

}
