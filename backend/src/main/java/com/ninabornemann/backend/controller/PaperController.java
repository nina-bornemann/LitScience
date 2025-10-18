package com.ninabornemann.backend.controller;
import com.ninabornemann.backend.model.OpenAlexResponse;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import com.ninabornemann.backend.service.OpenAlexService;
import com.ninabornemann.backend.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paper")
public class PaperController {

    private final PaperService paperService;

    private final OpenAlexService openAlexService;

    public PaperController(PaperService paperService, OpenAlexService openAlexService) {
        this.paperService = paperService;
        this.openAlexService = openAlexService;
    }

    @GetMapping
    public List<Paper> getAllPaper() {
        return paperService.getAllPaper();
    }

    @GetMapping("/import")
    public OpenAlexResponse importByDoi() {
        return openAlexService.getPaperByDoi();
    }

    @PostMapping
    public Paper addNewPaper(@RequestBody PaperDto paperDto) {
        return paperService.addNewpaper(paperDto);
    }
}
