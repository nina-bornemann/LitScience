package com.ninabornemann.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninabornemann.backend.TestFactory.TestPaperFactory;
import com.ninabornemann.backend.TestFactory.TestPaperScenario;
import com.ninabornemann.backend.model.Paper;
import com.ninabornemann.backend.service.OpenAiService;
import com.ninabornemann.backend.service.PaperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
class OpenAiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    OpenAiService openAiService;

    @MockitoBean
    PaperService paperService;

    @Test
    @WithMockUser
    void createReport_shouldReturn_paperWithReport() throws Exception {
        String report = "nice summary of the paper";
        TestPaperScenario p1 = new TestPaperFactory().createRandomTestPaperWithModification(paper -> paper.withReport(null));
        Paper p2 = p1.getPaper().withReport(report);

        when(paperService.getPaperById(any())).thenReturn(p1.getPaper());
        when(openAiService.createReport(p1.getPaper().title())).thenReturn(report);
        when(paperService.setReport(p1.getPaper().id(), report)).thenReturn(p2);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/report/" + p1.getPaper().id()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(p2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.report").isNotEmpty());
    }
}