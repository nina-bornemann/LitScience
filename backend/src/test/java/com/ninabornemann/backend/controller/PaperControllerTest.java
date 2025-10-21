package com.ninabornemann.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.model.PaperDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class PaperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private PaperRepo paperRepo;

    String jsonFrom(PaperDto dto) throws JsonProcessingException {
       return new ObjectMapper().writeValueAsString(dto);
    }

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

    @DirtiesContext
    @Test
    void getAllPapers_shouldReturn_EmptyListWhenEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/paper"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                                [
                                                                                ]
                                                                            """));
    }

    @DirtiesContext
    @Test
    void addNewPaper_shouldReturn_newPaper() throws Exception {
        PaperDto dto = new PaperDto("123", "gastruloids", "Ludi", 2022, "stem cells", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/paper")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFrom(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(""" 
                                                                               {
                                                                                  "doi": "123",
                                                                                  "title": "gastruloids",
                                                                                  "author": "Ludi",
                                                                                  "year": 2022,
                                                                                  "group": "stem cells",
                                                                                  "notes": ""
                                                                                }
                                                                           """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @DirtiesContext
    @Test
    void importPaperByDoi_shouldReturn_paperWhenDoiValid() throws Exception {
        mockServer.expect(requestTo("https://api.openalex.org/works/https://doi.org/123/567"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "title": "The state of OA: a large-scale analysis of the prevalence and impact of Open Access articles",
                            "publication_year": 2018,
                            "authorships": [
                                {
                                    "author_position": "first",
                                    "author": {
                                        "display_name": "Heather Piwowar"
                                    }
                                }
                            ]
                        }
                        """, MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paper/import/123/567"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                                {
                                                                                    "doi": "123/567",
                                                                                    "title": "The state of OA: a large-scale analysis of the prevalence and impact of Open Access articles",
                                                                                    "author": "Heather Piwowar",
                                                                                    "year": 2018,
                                                                                    "group": "",
                                                                                    "notes": ""
                                                                                }
                                                                                """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @DirtiesContext
    @Test
    void importPaperByDoi_should_whenDoiNotFound() throws Exception {
        mockServer.expect(requestTo("https://api.openalex.org/works/https://doi.org/123"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withResourceNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paper/import/123"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DirtiesContext
    @Test
    void getPaperById_shouldReturn_CorrectPaper() throws Exception {
        Paper p1 = new Paper("123", "456/678", "Cool article", "Einstein", 1920, "Physics", "nice");
        Paper p2 = new Paper("456", "123/456", "Another article", "Einstein", 1919, "", "");
        paperRepo.save(p1);
        paperRepo.save(p2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paper/456"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                               {
                                                                                   "id": "456",
                                                                                   "doi": "123/456",
                                                                                   "title": "Another article",
                                                                                   "author": "Einstein",
                                                                                   "year": 1919,
                                                                                   "group": "",
                                                                                   "notes": ""
                                                                               }
                                                                           """));
    }

    @DirtiesContext
    @Test
    void getPaperById_shouldThrow_ResponseStatusException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/paper/777"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                           {
                                                                             "errorMessage": "404 NOT_FOUND \\"No paper was found under this id.\\""
                                                                           }
                                                                           """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty());
    }

    @DirtiesContext
    @Test
    void deletePaperById_shouldDelete_whenIdFound() throws Exception {
        Paper p1 = new Paper("123", "456/789", "cool paper", "Einstein", 1920, "physics", "");
        paperRepo.save(p1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/paper/123"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());
    }

    @DirtiesContext
    @Test
    void deletePaperById_shouldThrow_ResponseStatusException_whenIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/paper/555"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                             {
                                                                               "errorMessage": "404 NOT_FOUND \\"No paper was found under this id.\\""
                                                                             }
                                                                           """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty());
    }
}