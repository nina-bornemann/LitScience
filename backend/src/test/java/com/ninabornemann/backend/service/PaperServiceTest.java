package com.ninabornemann.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.TestFactory.TestPaperFactory;
import com.ninabornemann.backend.TestFactory.TestPaperScenario;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaperServiceTest {

    TestPaperFactory testPaperFactory = new TestPaperFactory();

    @Test
    void getAllPaper_shouldReturn_listOfPaper() throws JsonProcessingException {
        PaperRepo mockRepo = mock(PaperRepo.class);
        IdService mockIdService = mock(IdService.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        TestPaperScenario p1 = testPaperFactory.createRandomTestPaper();
        TestPaperScenario p2 = testPaperFactory.createRandomTestPaper();
        List<Paper> papers = List.of(p1.getPaper(), p2.getPaper());


        when(mockRepo.findAll()).thenReturn(papers);
        List<Paper> actual = service.getAllPaper();

        verify(mockRepo).findAll();
        verifyNoMoreInteractions(mockRepo, mockIdService);
        assertEquals(papers, actual);
    }

    @Test
    void getAllPaper_shouldReturn_emptyList() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);

        when(mockRepo.findAll()).thenReturn(List.of());
        List<Paper> actual = service.getAllPaper();

        verify(mockRepo).findAll();
        verifyNoMoreInteractions(mockRepo, mockIdService);
        assertEquals(List.of(), actual);
    }

    @Test
    void addNewPaper_shouldReturn_newPaper() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        PaperDto dto = new PaperDto("123", "gastruloids", "Ludi", 2022, List.of("stem cells"), "");
        Paper newPaper = new Paper("Test-id", "123", "gastruloids", "Ludi", 2022, List.of("stem cells"), "", false);

        when(mockIdService.randomId()).thenReturn("Test-id");
        when(mockRepo.save(newPaper)).then(a -> a.getArgument(0));
        Paper actual = service.addNewpaper(dto);

        verify(mockIdService).randomId();
        verify(mockRepo).save(newPaper);
        verifyNoMoreInteractions(mockIdService, mockRepo);
        assertEquals(newPaper, actual);
    }

    @Test
    void getPaperById_shouldReturn_correctPaper_whenIdFound() throws JsonProcessingException {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        TestPaperScenario p1 = testPaperFactory.createRandomTestPaper();

        when(mockRepo.findById(p1.getPaper().id())).thenReturn(Optional.of(p1.getPaper()));
        Paper actual = service.getPaperById(p1.getPaper().id());

        assertEquals(p1.getPaper(), actual);
        assertDoesNotThrow(() -> service.getPaperById(p1.getPaper().id()));
        verify(mockRepo, atMost(2)).findById(p1.getPaper().id());
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
    void deletePaperById_shouldReturn_noContent() throws JsonProcessingException {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        TestPaperScenario p1 = testPaperFactory.createRandomTestPaper();

        when(mockRepo.findById(p1.getPaper().id())).thenReturn(Optional.of(p1.getPaper()));
        doNothing().when(mockRepo).delete(p1.getPaper());
        service.deletePaperById(p1.getPaper().id());

        verify(mockRepo).findById(p1.getPaper().id());
        verify(mockRepo).delete(p1.getPaper());
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
    void editPaperById_shouldReturn_updatedPaper() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        Paper existing = new Paper("123", "234", "Title", "Author", 2002, List.of("Science"), "", true);
        PaperDto dto = new PaperDto("234", "Title", "Author", 2004, List.of("Better Group"), "some notes");
        Paper updated = new Paper("123", "234", "Title", "Author", 2004, List.of("Better Group"), "some notes", true);

        when(mockRepo.findById("123")).thenReturn(Optional.of(existing));
        when(mockRepo.save(updated)).then(a -> a.getArgument(0));
        Paper actual = service.editPaperById("123", dto);

        verify(mockRepo).findById("123");
        verify(mockRepo).save(updated);
        verifyNoMoreInteractions(mockRepo, mockIdService);
        assertEquals(updated, actual);
    }

    @Test
    void editPaperById_shouldThrowException() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        PaperDto dto = new PaperDto("234", "Title", "Author", 2004, List.of("Better Group"), "some notes");

        when(mockRepo.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.editPaperById("999", dto));
        verify(mockRepo).findById("999");
        verifyNoMoreInteractions(mockIdService, mockRepo);
    }

    @Test
    void toggleFavoriteById_shouldReturn_oppositeBoolean() throws JsonProcessingException {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        TestPaperScenario p = testPaperFactory.createRandomTestPaperWithModification((paper) -> paper.withFav(true));
        Paper toggled = p.getPaper().withFav(false);

        when(mockRepo.findById(p.getPaper().id())).thenReturn(Optional.of(p.getPaper()));
        when(mockRepo.save(toggled)).then(a -> a.getArgument(0));
        Paper actual = service.toggleFavoriteById(p.getPaper().id());

        verify(mockRepo).findById(p.getPaper().id());
        verify(mockRepo).save(toggled);
        verifyNoMoreInteractions(mockIdService, mockRepo);
        assertEquals(toggled, actual);
    }

    @Test
    void toggleFavoriteById_shouldThrow_ResponseStatusException() {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);

        when(mockRepo.findById("2")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.toggleFavoriteById("2"));
        verify(mockRepo).findById("2");
        verifyNoMoreInteractions(mockIdService, mockRepo);
    }

    @Test
    void editGroupsById_shouldReturn_updatedPaper() throws JsonProcessingException {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        TestPaperScenario p = testPaperFactory.createRandomTestPaper();
        List<String> groupTags = List.of("bio", "chem");
        Paper updated = p.getPaper().withGroup(groupTags);

        when(mockRepo.findById(p.getPaper().id())).thenReturn(Optional.of(p.getPaper()));
        when(mockRepo.save(updated)).thenReturn(updated);
        Paper actual = service.editGroupsById(p.getPaper().id(), groupTags);

        verify(mockRepo).findById(p.getPaper().id());
        verify(mockRepo).save(updated);
        verifyNoMoreInteractions(mockIdService, mockRepo);
        assertEquals(updated, actual);
    }

    @Test
    void editGroupsById_shouldThrow_ResponseStatusException_whenIdNotFound() throws JsonProcessingException {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        TestPaperScenario p = testPaperFactory.createRandomTestPaper();
        List<String> groupTags = List.of("bio", "chem");
        Paper updated = p.getPaper().withGroup(groupTags);

        when(mockRepo.findById("123")).thenReturn(Optional.empty());
        when(mockRepo.save(updated)).thenReturn(updated);

        assertThrows(ResponseStatusException.class, () -> service.editGroupsById("123", groupTags));
        verify(mockRepo).findById("123");
        verifyNoMoreInteractions(mockIdService, mockRepo);
    }

    @Test
    void findByGroup_shouldReturn_PaperListOfGroup() throws JsonProcessingException {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);
        TestPaperScenario p1 = testPaperFactory.createRandomTestPaperWithModification((p) -> p.withGroup(List.of("bio", "chem")));
        TestPaperScenario p2 = testPaperFactory.createRandomTestPaperWithModification((p) -> p.withGroup(List.of("physics", "literature")));
        TestPaperScenario p3 = testPaperFactory.createRandomTestPaperWithModification((p) -> p.withGroup(List.of("literature", "bio")));
        TestPaperScenario p4 = testPaperFactory.createRandomTestPaperWithModification((p) -> p.withGroup(List.of("chem", "geo")));
        List<Paper> papers = List.of(p2.getPaper(), p3.getPaper());

        when(mockRepo.findAll()).thenReturn(papers);
        List<Paper> actual = service.findByGroup("literature");

        verify(mockRepo).findAll();
        verifyNoMoreInteractions(mockRepo, mockIdService);
        assertEquals(papers, actual);
    }

    @Test
    void findByGroup_shouldReturn_EmptyList() throws JsonProcessingException {
        IdService mockIdService = mock(IdService.class);
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockIdService, mockRepo);

        when(mockRepo.findAll()).thenReturn(new ArrayList<>());
        List<Paper> actual = service.findByGroup("physics");

        verify(mockRepo).findAll();
        verifyNoMoreInteractions(mockRepo, mockIdService);
        assertEquals(new ArrayList<>(), actual);
    }
}