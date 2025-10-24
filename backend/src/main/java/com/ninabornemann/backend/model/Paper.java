package com.ninabornemann.backend.model;

import lombok.With;

public record Paper(String id,
                    String doi,
                    String title,
                    String author,
                    int year,
                    String group,
                    String notes,
                    @With boolean isFav
                    ) {
}
