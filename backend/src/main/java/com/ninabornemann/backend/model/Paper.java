package com.ninabornemann.backend.model;

public record Paper(String id,
                    String doi,
                    String title,
                    String author,
                    int year,
                    String group,
                    String notes,
                    boolean isFav
                    ) {
}
