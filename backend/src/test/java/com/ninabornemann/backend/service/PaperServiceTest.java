package com.ninabornemann.backend.service;
import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaperServiceTest {

    @Test
    void getAllPaper_shouldReturn_listOfPaper() {
        PaperRepo mockRepo = mock(PaperRepo.class);
        IdService mockIdService = mock(IdService.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        Paper p1 = new Paper("1", "1.2/3", "Test1", "Tester", 2024, "Bio", "nice");
        Paper p2 = new Paper("2", "1.3/5", "Test2", "Prof", 2019, "Physics", "cool");
        List<Paper> papers = List.of(p1, p2);


        when(mockRepo.findAll()).thenReturn(papers);
        List<Paper> actual = service.getAllPaper();

        verify(mockRepo).findAll();
        assertEquals(papers, actual);
        verifyNoMoreInteractions(mockRepo, mockIdService);
    }

    @Test
    void getAllPaper_shouldReturn_emptyList() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);

        when(mockRepo.findAll()).thenReturn(List.of());
        List<Paper> actual = service.getAllPaper();

        assertEquals(List.of(), actual);
        verify(mockRepo).findAll();
        verifyNoMoreInteractions(mockRepo, mockIdService);
    }

    @Test
    void addNewPaper_shouldReturn_newPaper() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        PaperDto dto = new PaperDto("123", "gastruloids", "Ludi", 2022, "stem cells", "");
        Paper newPaper = new Paper("Test-id", "123", "gastruloids", "Ludi", 2022, "stem cells", "");

        when(mockIdService.randomId()).thenReturn("Test-id");
        when(mockRepo.save(newPaper)).thenReturn(newPaper);
        Paper actual = service.addNewpaper(dto);

        assertEquals(newPaper, actual);
        verify(mockIdService).randomId();
        verify(mockRepo).save(newPaper);
        verifyNoMoreInteractions(mockIdService, mockRepo);

    }

    @Test
    void getPaperById_shouldReturn_correctPaper_whenIdFound() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        Paper p1 = new Paper("123", "456/678", "Cool article", "Einstein", 1920, "Physics", "nice");

        when(mockRepo.findById("456")).thenReturn(Optional.of(p1));
        Paper actual = service.getPaperById("456");

        assertEquals(p1, actual);
        assertDoesNotThrow(() -> service.getPaperById("456"));
        verify(mockRepo, atMost(2)).findById("456");
        verifyNoMoreInteractions(mockIdService, mockRepo);
    }

    @Test
    void getPaperById_shouldThrow_ResponseStatusException() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);

        when(mockRepo.findById("888")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getPaperById("888"));
        verify(mockRepo).findById("888");
        verifyNoMoreInteractions(mockIdService, mockRepo);
    }

    @Test
    void deletePaperById_shouldReturn_noContent() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        Paper p1 = new Paper("123", "456/789", "cool paper", "Einstein", 1920, "physics", "");

        when(mockRepo.findById("123")).thenReturn(Optional.of(p1));
        doNothing().when(mockRepo).delete(p1);
        service.deletePaperById("123");

        verify(mockRepo).findById("123");
        verify(mockRepo).delete(p1);
        verifyNoMoreInteractions(mockRepo, mockIdService);
    }

    @Test
    void deletePaperById_shouldThrowException_whenIdNotFound() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);

        when(mockRepo.findById("666")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.deletePaperById("666"));
        verify(mockRepo).findById("666");
        verifyNoMoreInteractions(mockIdService, mockRepo);
    }

    @Test
    void editPaperBxId_shouldReturn_updatedPaper() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        Paper existing = new Paper("123", "234", "Title", "Author", 2002, "Science", "");
        PaperDto dto = new PaperDto("234", "Title", "Author", 2004, "Better Group", "some notes");
        Paper updated = new Paper("123", "234", "Title", "Author", 2004,"Better Group", "some notes");

        when(mockRepo.findById("123")).thenReturn(Optional.of(existing));
        when(mockRepo.save(updated)).thenReturn(updated);
        Paper actual = service.editPaperById("123", dto);

        assertEquals(updated, actual);
        verify(mockRepo).findById("123");
        verify(mockRepo).save(updated);
        verifyNoMoreInteractions(mockRepo, mockIdService);
    }

    @Test
    void editPaperById_shouldThrowException() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        PaperDto dto = new PaperDto("234", "Title", "Author", 2004, "Better Group", "some notes");

        when(mockRepo.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.editPaperById("999", dto));
        verify(mockRepo).findById("999");
        verifyNoMoreInteractions(mockIdService, mockRepo);
    }
}