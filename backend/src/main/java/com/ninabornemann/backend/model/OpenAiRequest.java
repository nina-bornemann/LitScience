package com.ninabornemann.backend.model;

import java.util.List;

public record OpenAiRequest(String model, List<OpenAiMessage> messages) {
}
