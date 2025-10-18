package com.ninabornemann.backend.controller;
import com.ninabornemann.backend.model.OpenAlexResponse;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import com.ninabornemann.backend.service.OpenAlexService;
import com.ninabornemann.backend.service.PaperService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/import/**")
    public Paper importByDoi(HttpServletRequest request) {
        // because there are slashes in doi spring path matching does not work, need to get request and parse doi manually
        String requestUri = request.getRequestURI();
        // parts are expected to be "basePath/api/paper" and "doi" because import is cut out
        String[] parts = requestUri.split("/import/");
        String doi = parts[1];
        PaperDto paperDto = openAlexService.getPaperByDoi(doi);
        return paperService.addNewpaper(paperDto);
    }

    @PostMapping
    public Paper addNewPaper(@RequestBody PaperDto paperDto) {
        return paperService.addNewpaper(paperDto);
    }
}
