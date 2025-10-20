package com.ninabornemann.backend.service;
import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PaperService {

    private final IdService idService;
    private final PaperRepo paperRepo;

    public PaperService(IdService idService, PaperRepo paperRepo) {
        this.idService = idService;
        this.paperRepo = paperRepo;
    }

    public List<Paper> getAllPaper() {
        return paperRepo.findAll();
    }

    public Paper addNewpaper(PaperDto paperDto) {
        Paper newPaper = new Paper(idService.randomId(), paperDto.doi(), paperDto.title(), paperDto.author(), paperDto.year(), paperDto.group(), paperDto.notes());
        return paperRepo.save(newPaper);
    }

    public void deletePaperById(String id) {
        Paper existing = paperRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No paper was found under this id."));
        paperRepo.delete(existing);
    }
}
