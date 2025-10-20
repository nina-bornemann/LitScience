package com.ninabornemann.backend.model;

import java.util.List;

public record OpenAlexResponse(
        String title,
        List<Authorship> authorships,
        OpenAccess open_access,
        int publication_year
) {

    public record OpenAccess(
            boolean is_oa,
            String oa_url
    ){}

    public record Authorship(
            String author_position,
            Author author
    ){}

    public record Author(
            String display_name
    ){}


}
