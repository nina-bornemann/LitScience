package com.ninabornemann.backend.model;

import java.util.List;

public record PaperDto(String doi,
                       String title,
                       String author,
                       int year,
                       List<String> group,
                       String notes
) {
}
