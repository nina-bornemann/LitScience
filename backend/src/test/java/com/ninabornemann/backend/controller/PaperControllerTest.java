package com.ninabornemann.backend.controller;

import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.model.Paper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PaperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaperRepo paperRepo;

    @DirtiesContext
    @Test
    void getAllPaper_shouldReturn_listOfPaper() throws Exception {
        Paper p1 = new Paper("1", "1.2/3", "Test1", "Tester", 2024, "Bio", "nice");
        Paper p2 = new Paper("2", "1.3/5", "Test2", "Prof", 2019, "Physics", "cool");
        paperRepo.save(p1);
        paperRepo.save(p2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paper"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                           [
                                                                             {
                                                                               "id": "1",
                                                                               "doi": "1.2/3",
                                                                               "title": "Test1",
                                                                               "author": "Tester",
                                                                               "year": 2024,
                                                                               "group": "Bio",
                                                                               "notes": "nice"
                                                                             },
                                                                             {
                                                                               "id": "2",
                                                                               "doi": "1.3/5",
                                                                               "title": "Test2",
                                                                               "author": "Prof",
                                                                               "year": 2019,
                                                                               "group": "Physics",
                                                                               "notes": "cool"
                                                                             }
                                                                           ]
                                                                           """));
    }
}