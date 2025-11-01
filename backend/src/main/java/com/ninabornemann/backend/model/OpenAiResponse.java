package com.ninabornemann.backend.model;

import java.util.List;

public record OpenAiResponse(List<OpenAiChoice> choices) {
}
