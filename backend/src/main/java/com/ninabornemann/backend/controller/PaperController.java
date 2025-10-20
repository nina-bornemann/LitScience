package com.ninabornemann.backend.controller;
import com.ninabornemann.backend.exceptions.DoiNotFoundException;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import com.ninabornemann.backend.service.OpenAlexService;
import com.ninabornemann.backend.service.PaperService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Paper> importByDoi(HttpServletRequest request) {
        // because there are slashes in doi spring path matching does not work, need to get request and parse doi manually
        String requestUri = request.getRequestURI();
        // parts are expected to be "basePath/api/paper" and "doi" because import is cut out
        String[] parts = requestUri.split("/import/");
        String doi = parts[1];
        try {
            PaperDto paperDto = openAlexService.getPaperByDoi(doi);
            Paper newPaper = paperService.addNewpaper(paperDto);
            return ResponseEntity.ok(newPaper);
        }
        catch (DoiNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Paper addNewPaper(@RequestBody PaperDto paperDto) {
        return paperService.addNewpaper(paperDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Paper> deletePaperById(@PathVariable String id) {
        paperService.deletePaperById(id);
        return ResponseEntity.noContent().build();
    }
}
