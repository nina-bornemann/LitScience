package com.ninabornemann.backend.service;

import com.ninabornemann.backend.Repo.PaperRepo;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    private final IdService idService;
    private final PaperRepo paperRepo;

    public OpenAiService(IdService idService, PaperRepo paperRepo) {
        this.idService = idService;
        this.paperRepo = paperRepo;
    }


}
