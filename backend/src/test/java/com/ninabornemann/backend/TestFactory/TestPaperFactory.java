package com.ninabornemann.backend.TestFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninabornemann.backend.model.Paper;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

public class TestPaperFactory {

    List<String> group = List.of("bio", "pastry", "chem", "physics", "literature", "language", "protein", "DNA");
    List<String> title = List.of("foo",  "bar",  "test", "title", "animals");
    List<String> doi = List.of("12.3",  "23.4/5",  "1.2/3", "98.10/2", "56.7/8");
    List<String> author = List.of("Einstein",  "Curie",  "Pancake", "Tester", "Newton");
    List<Integer> year = List.of(1957,  2012,  2018, 1977, 1999);
    List<String> notes = List.of("cool",  "nice",  "interesting", "relevant", "nice to know");
    List<Boolean> isFav = List.of(true,  false);

    private final ObjectMapper mapper = new ObjectMapper();

    private <T> T getRandom(List<T> choices) {
        return choices.get(new Random().nextInt(0, choices.size()));
    }

    public TestPaperScenario createRandomTestPaper() throws JsonProcessingException {
        Paper paper = new Paper(UUID.randomUUID().toString(),
                getRandom(doi),
                getRandom(title),
                getRandom(author),
                getRandom(year),
                List.of(getRandom(group), getRandom(group)),
                getRandom(notes),
                getRandom(isFav));
        return new TestPaperScenario(paper, mapper.writeValueAsString(paper));
    }

    public TestPaperScenario createRandomTestPaperWithModification(Function<Paper, Paper> modification) throws JsonProcessingException {
        TestPaperScenario p = createRandomTestPaper();
        Paper modified = modification.apply(p.paper);
        return new TestPaperScenario(modified, mapper.writeValueAsString(modified));
    }
}
