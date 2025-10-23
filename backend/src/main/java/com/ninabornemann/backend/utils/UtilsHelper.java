package com.ninabornemann.backend.utils;

import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;

public class UtilsHelper {

    private UtilsHelper() {
    }

    public static Paper transformDtoToPaper(PaperDto dto, Paper existing) {
        return new Paper(existing.id(),
                dto.doi() != null ? dto.doi() : existing.doi(),
                dto.title() != null ? dto.title() : existing.title(),
                dto.author() != null ? dto.author() : existing.author(),
                dto.year() != 0 ? dto.year() : existing.year(),
                dto.group() != null ? dto.group() : existing.group(),
                dto.notes() != null ? dto.notes() : existing.notes());
    }
}
