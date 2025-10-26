package com.ninabornemann.backend.model;

import lombok.With;
import java.util.List;

public record Paper(String id,
                    String doi,
                    String title,
                    String author,
                    int year,
                    List<String> group,
                    String notes,
                    @With boolean isFav
                    ) {
}
