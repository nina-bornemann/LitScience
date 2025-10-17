package com.ninabornemann.backend.service;
import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.model.Paper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {

    private final PaperRepo paperRepo;

    public PaperService(PaperRepo paperRepo) {
        this.paperRepo = paperRepo;
    }

    public List<Paper> getAllPaper() {
        return paperRepo.findAll();
    }
}
