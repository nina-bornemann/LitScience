package com.ninabornemann.backend.controller;

import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.service.OpenAiService;
import com.ninabornemann.backend.service.PaperService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/report")
public class OpenAiController {

    private final OpenAiService openAiService;
    private final PaperService paperService;

    public OpenAiController(OpenAiService openAiService, PaperService paperService) {
        this.openAiService = openAiService;
        this.paperService =paperService;
    }

    @PostMapping("/{id}")
    public Paper createReport(@PathVariable String id) {
        Paper paper = paperService.getPaperById(id);
        String report = openAiService.createReport(paper.title());
        return paperService.setReport(paper.id(), report);
    }

}
