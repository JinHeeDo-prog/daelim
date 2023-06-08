package com.example.ch16.config.security.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().version("v1.0.0")
                .title("CH16 API").description("Spirng JPA Project");
        return new OpenAPI().info(info);
    }
}
