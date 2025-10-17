package com.ninabornemann.backend.model;

public record PaperDto(String doi,
                       String title,
                       String author,
                       int year,
                       String group,
                       String notes
) {
}
