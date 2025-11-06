package com.ninabornemann.backend.service;

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

    private final RestClient restClient;
    private final String apiKey;

    public OpenAiService(RestClient client, @Value("${OPENAI_API_KEY}") String apiKey) {
        this.restClient = client;
        this.apiKey = apiKey;
    }

    public String createReport(@RequestBody String title) {
        OpenAiResponse response = restClient.post()
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
                                5️⃣ The references follow this structure:
                                
                                    [number]. Author(s). Title. Journal. Year;Volume:Pages. [DOI] [PubMed] [Google Scholar]
                                
                                    IMPORTANT RULES:
                                    1. If the references only contain the placeholder "[DOI]" without the actual DOI number, you MUST NOT invent or guess any DOI.
                                    2. Instead, you may list the *titles* of those references as "potentially related studies", but only if the title text is explicitly visible in the provided text.
                                    3. If the title is not visible, say exactly: "No reference titles visible."
                                    4. NEVER make up or guess any new DOI, paper title, or author.
                                    5. Summarize the paper as requested below, then add a section called "Referenced Related Studies" that includes ONLY:
                                       - Items where a real DOI number or visible title is present.
                                    6. If no real DOIs or titles are available, clearly state: "No valid references found."
                                
                                Be concise, factual, and fully based on the source material.
                                If unsure about any information, omit it rather than guessing.
                                """),
                        new OpenAiMessage("user", title))))
                .retrieve()
                .body(OpenAiResponse.class);
        return response.choices().getFirst().message().content();
    }

}
