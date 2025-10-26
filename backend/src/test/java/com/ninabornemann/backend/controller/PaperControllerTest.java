package com.ninabornemann.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninabornemann.backend.Repo.PaperRepo;
import com.ninabornemann.backend.TestFactory.TestPaperFactory;
import com.ninabornemann.backend.TestFactory.TestPaperScenario;
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

import java.util.List;

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

    TestPaperFactory testPaperFactory = new TestPaperFactory();

    @DirtiesContext
    @Test
    void getAllPaper_shouldReturn_listOfPaper() throws Exception {
        TestPaperScenario p1 = testPaperFactory.createRandomTestPaper();
        TestPaperScenario p2 = testPaperFactory.createRandomTestPaper();
        paperRepo.save(p1.getPaper());
        paperRepo.save(p2.getPaper());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paper"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(List.of(p1.getPaper(), p2.getPaper()))));
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
        PaperDto dto = new PaperDto("123", "gastruloids", "Ludi", 2022, List.of("stem cells", "gastruloids"), "");
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
                                                                                  "group": ["stem cells", "gastruloids"],
                                                                                  "notes": "",
                                                                                  "isFav": false
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
                                                                                    "group": [""],
                                                                                    "notes": "",
                                                                                    "isFav": false
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
        TestPaperScenario p1 = testPaperFactory.createRandomTestPaper();
        TestPaperScenario p2 = testPaperFactory.createRandomTestPaper();
        paperRepo.save(p1.getPaper());
        paperRepo.save(p2.getPaper());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paper/" +p1.getPaper().id()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(p1.getJson()));
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
        TestPaperScenario p1 = testPaperFactory.createRandomTestPaper();
        paperRepo.save(p1.getPaper());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/paper/" + p1.getPaper().id()))
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

    @DirtiesContext
    @Test
    void editPaperById_shouldReturn_updatedPaper() throws Exception {
        Paper existing = new Paper("234", "123.4/56", "Plant metabolism", "Prof", 1970, List.of("plants"), "", false);
        paperRepo.save(existing);
        PaperDto updated = new PaperDto("123.4/56", "Plant metabolism", "Prof", 1970, List.of("plants"), "cool paper");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/paper/234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFrom(updated))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                           {
                                                                               "id": "234",
                                                                               "doi": "123.4/56",
                                                                               "title": "Plant metabolism",
                                                                               "author": "Prof",
                                                                               "year": 1970,
                                                                               "group": ["plants"],
                                                                               "notes": "cool paper",
                                                                               "isFav": false
                                                                           }
                                                                           """));
    }

    @DirtiesContext
    @Test
    void editPaperById_shouldThrow_ResponseStatusException_whenIdNotFound() throws Exception {
        PaperDto dto = new PaperDto("22.3/4", "Gastruloids", "Krauss", 2022, List.of("stem cells"), "important");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/paper/333")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFrom(dto))
                )
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
    void toggleFavoriteById_shouldReturn_oppositeBoolean() throws Exception {
        TestPaperScenario p = testPaperFactory.createRandomTestPaperWithModification((paper) -> paper.withFav(false));
        paperRepo.save(p.getPaper());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/paper/" +p.getPaper().id()+ "/favorite"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFav").value(true));
    }

    @DirtiesContext
    @Test
    void toggleFavoriteById_shouldThrow_ResponseStatusException_whenIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/paper/246/favorite"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                                           {
                                                                             "errorMessage": "404 NOT_FOUND \\"No paper was found under this id.\\""
                                                                           }
                                                                           """));
    }
}