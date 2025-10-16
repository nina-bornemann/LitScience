package com.ninabornemann.backend.service;
import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.model.Paper;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaperServiceTest {

    @Test
    void getAllPaper_shouldReturn_listOfPaper() {
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockRepo);
        Paper p1 = new Paper("1", "1.2/3", "Test1", "Tester", 2024, "Bio", "nice");
        Paper p2 = new Paper("2", "1.3/5", "Test2", "Prof", 2019, "Physics", "cool");
        List<Paper> papers = List.of(p1, p2);


        when(mockRepo.findAll()).thenReturn(papers);
        List<Paper> actual = service.getAllPaper();

        verify(mockRepo).findAll();
        assertEquals(papers, actual);
        verifyNoMoreInteractions(mockRepo);
    }

    @Test
    void getAllPaper_shouldReturn_emptyList() {
        PaperRepo mockRepo = mock(PaperRepo.class);
        PaperService service = new PaperService(mockRepo);

        when(mockRepo.findAll()).thenReturn(List.of());
        List<Paper> actual = service.getAllPaper();

        verify(mockRepo).findAll();
        verifyNoMoreInteractions(mockRepo);
    }
}