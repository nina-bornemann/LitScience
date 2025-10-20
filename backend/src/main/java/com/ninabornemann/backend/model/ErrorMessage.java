package com.ninabornemann.backend.model;

import java.time.Instant;

public record ErrorMessage(String errorMessage, Instant instant) {
}
