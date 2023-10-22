package com.binaracademy.binarfud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {
//    @Value("${server.servlet.context-path}")
//    private String contextPath;
//    @Value("${server.port:8080}")
//    private int serverPort;

    @Bean
    public OpenAPI api(@Value("Swagger UI for BinarFud") String appDescription,
                                  @Value("v1.0.0") String appVersion
    ) {
        return new OpenAPI()
                .info(new Info()
                        .title("BinarFud")
                        .version(appVersion)
                        .description(appDescription)
                );
    }
}

