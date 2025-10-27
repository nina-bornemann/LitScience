package com.ninabornemann.backend.TestFactory;

import com.ninabornemann.backend.model.Paper;

public class TestPaperScenario {
    Paper paper;
    String json;

    public TestPaperScenario(Paper paper, String s) {
        this.paper = paper;
        this.json = s;
    }

    public String getJson() {
        return json;
    }

    public Paper getPaper() {
        return paper;
    }
}
