package com.ninabornemann.backend.model;

import lombok.With;
import java.util.List;

@With
public record Paper(String id,
                    String doi,
                    String title,
                    String author,
                    int year,
                    List<String> group,
                    String notes,
                    boolean isFav,
                    String report
                    ) {
}
