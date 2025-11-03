/*
package com.ninabornemann.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ApiService.class)
class OpenAiServiceTest {

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    OpenAiService service;


    @Test
    void generateReport_shouldReturn_summarizedReport() throws Exception {
        String title = "Capturing Cardiogenesis in Gastruloids";
        mockServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("""
                        {
                            "choices": [
                              {
                                "message": {
                                  "content": "This is a summary about this very interesting paper!"
                                }
                              }
                            ]
                        }""", MediaType.APPLICATION_JSON));

        String actual = service.createReport(title);

        assertEquals("This is a summary about this very interesting paper!", actual);
    }
}*/
