package com.ninabornemann.backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paper")
public record Paper(@Id String id) {
}
