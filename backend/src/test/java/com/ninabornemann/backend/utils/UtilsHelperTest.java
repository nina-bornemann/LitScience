package com.ninabornemann.backend.utils;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsHelperTest {

    @Test
    void transformDtoToPaper_shouldReturn_CorrectPaper(){
        Paper p = new Paper("1", "23", "Test", "Tester", 1999, List.of(""), "", false);
        PaperDto dto = new PaperDto(null, null, null, 0, null, "Experiments to do");
        Paper updated = new Paper("1", "23", "Test", "Tester", 1999, List.of(""), "Experiments to do", false);

        Paper actual = UtilsHelper.transformDtoToPaper(dto, p);

        assertEquals(updated, actual);
    }
}