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
                        new OpenAiMessage("system",
                                """
                                You are a factual scientific summarizer.
                                Always base your responses strictly on provided paper text.
                                Never invent, infer, or guess any data.
                                """),
                        new OpenAiMessage("user",
                                """
                                Please generate a structured, readable report about the scientific paper with the given title.

                                STYLE REQUIREMENTS:
                                - Write clear paragraphs separated by visible line breaks using \\n (for HTML rendering).
                                - Use fun and fitting emojis at the start of each paragraph.
                                - DO NOT use Markdown or HTML tags.
                                - Each section should have a clear title in all caps (e.g., "ABSTRACT", "METHODS", "KEY FINDINGS", "DISCUSSION", "LIMITATIONS").
                                - Keep a friendly but scientific tone.

                                CONTENT REQUIREMENTS:
                                1️⃣ Summarize the abstract in 2 sentences.
                                2️⃣ Describe the key scientific methods, reagents, and relevant metrics.
                                3️⃣ Summarize the main findings and their scientific relevance.
                                4️⃣ Discuss limitations or open questions from the study.
                                5️⃣ At the end, extract **only titles that appear verbatim in the References section of the paper**.
                                    - Use only titles that literally appear in the References text and the corresponding doi to that paper.
                                    - Do not guess, infer, or invent any DOIs.
                                    - If no valid DOIs are present, say: "No DOIs listed in the references section."
                                    - Never fabricate DOIs or other identifiers.

                                Be concise, factual, and fully based on the source material.
                                If unsure about any information, omit it rather than guessing.
                                """),
                        new OpenAiMessage("user", title))))
                .retrieve()
                .body(OpenAiResponse.class);
        return response.choices().getFirst().message().content();
    }

}
