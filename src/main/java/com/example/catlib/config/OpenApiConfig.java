package com.example.catlib.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI catLibOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CatLib API")
                        .description("API for fetching cat images and book metadata by topic")
                        .version("1.0.0"));
    }
}