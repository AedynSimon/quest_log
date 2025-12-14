package com.example.questlog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QuestLog API")
                        .description("A tiny quest tracker API with check-ins and streak analytics.")
                        .version("1.0.0"));
    }
}
