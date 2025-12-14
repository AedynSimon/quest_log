package com.example.questlog.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "questlog.demo-data=false")
@AutoConfigureMockMvc
class QuestApiTest {

    @Autowired
    MockMvc mvc;

    @Test
    void canCreateAndFetchQuest() throws Exception {
        String body = """
                {
                  \"title\": \"Ship QuestLog\",
                  \"description\": \"End-to-end demo\",
                  \"priority\": 5,
                  \"status\": \"ACTIVE\",
                  \"tags\": [\"demo\", \"spring\"]
                }
                """;

        mvc.perform(post("/api/quests")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Ship QuestLog"));

        mvc.perform(get("/api/quests")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void apiRequiresAuth() throws Exception {
        mvc.perform(get("/api/quests"))
                .andExpect(status().isUnauthorized());
    }
}
