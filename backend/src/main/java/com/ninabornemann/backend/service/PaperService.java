package com.ninabornemann.backend.service;
import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import com.ninabornemann.backend.utils.UtilsHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class PaperService {

    private final IdService idService;
    private final PaperRepo paperRepo;

    String idNotFoundMessage = "No paper was found under this id.";

    public PaperService(IdService idService, PaperRepo paperRepo) {
        this.idService = idService;
        this.paperRepo = paperRepo;
    }

    public List<Paper> getAllPaper() {
        return paperRepo.findAll();
    }

    public Paper addNewpaper(PaperDto paperDto) {
        Paper newPaper = new Paper(idService.randomId(), paperDto.doi(), paperDto.title(), paperDto.author(), paperDto.year(), paperDto.group(), paperDto.notes(), false);
        return paperRepo.save(newPaper);
    }

    public Paper getPaperById(String id) {
        return paperRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, idNotFoundMessage));
    }

    public void deletePaperById(String id) {
        Paper existing = paperRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, idNotFoundMessage));
        paperRepo.delete(existing);
    }

    public Paper editPaperById(String id, PaperDto dto) {
        Paper existing = paperRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, idNotFoundMessage));
        Paper updated = UtilsHelper.transformDtoToPaper(dto, existing);
        return paperRepo.save(updated);
    }

    @Transactional
    public Paper toggleFavoriteById(String id) {
        Paper existing = paperRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, idNotFoundMessage));
        return paperRepo.save(existing.withFav(!existing.isFav()));
    }

    public Paper editGroupsById(String id, List<String> groupTags) {
        Paper existing = paperRepo.findById(id)
                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND, idNotFoundMessage));
        return paperRepo.save(new Paper(existing.id(),
                existing.doi(),
                existing.title(),
                existing.author(),
                existing.year(),
                groupTags,
                existing.notes(),
                existing.isFav()));
    }

    public List<Paper> findByGroup(String group) {
        return paperRepo.findAll().stream()
                .filter(p -> p.group().contains(group))
                .toList();
    }
}
