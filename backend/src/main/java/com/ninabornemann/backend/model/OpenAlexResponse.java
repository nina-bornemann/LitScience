package com.ninabornemann.backend.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OpenAlexResponse(
        String title,
        List<Authorship> authorships,
        @JsonProperty("open_access") OpenAccess openAccess,
        @JsonProperty("publication_year") int publicationYear
) {

    public record OpenAccess(
            @JsonProperty("is_oa") boolean isOa,
            @JsonProperty("oa_url") String oaUrl
    ){}

    public record Authorship(
            @JsonProperty("author_position") String authorPosition,
            Author author
    ){}

    public record Author(
            @JsonProperty("display_name") String displayName
    ){}


}
