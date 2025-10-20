package com.ninabornemann.backend.service;
import com.ninabornemann.backend.exceptions.DoiNotFoundException;
import com.ninabornemann.backend.model.PaperDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(OpenAlexService.class)
class OpenAlexServiceTest {

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    OpenAlexService service;

    @Test
    void getPaperByDoi() throws DoiNotFoundException {
        PaperDto paperDto = new PaperDto("123/456", "new article", "Nina Bornemann", 2020, "", "");

        mockServer.expect(ExpectedCount.max(2), requestTo("https://api.openalex.org/works/https://doi.org/123/456"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "title": "new article",
                            "publication_year": 2020,
                            "authorships": [
                                {
                                    "author_position": "first",
                                    "author": {
                                        "display_name": "Nina Bornemann"
                                    }
                                }
                            ]
                        }
                        """, MediaType.APPLICATION_JSON));

        assertEquals(paperDto, service.getPaperByDoi("123/456"));
        assertDoesNotThrow(() -> service.getPaperByDoi("123/456"));
    }

    @Test
    void getPaperByDoi_shouldThrowException_whenDoiNotFound() {
        mockServer.expect(requestTo("https://api.openalex.org/works/https://doi.org/777"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withResourceNotFound());

        assertThrows(DoiNotFoundException.class, () -> service.getPaperByDoi("777"));
    }
}