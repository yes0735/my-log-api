package com.pllis.mylog.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

/**
 * Swagger 설정
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(
        @Value("${spring.profiles.active:local}") String activeProfile
    ) {
        String securityJwtName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
        Components components = new Components()
            .addSecuritySchemes(securityJwtName, new SecurityScheme()
                .name(securityJwtName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat(securityJwtName));

        return new OpenAPI()
            .info(
                new Info()
                    .title(String.format("[%s] My LOG API", activeProfile))
                    .version("v1")
                    .description("My LOG API 문서")
            )
            .addSecurityItem(securityRequirement)
            .addServersItem(new Server().url("/"))
            .components(components);
    }

}