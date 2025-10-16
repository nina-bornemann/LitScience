package com.ninabornemann.backend.controller;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.service.PaperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/paper")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping
    public List<Paper> getAllPaper() {
        return paperService.getAllPaper();
    }
}
