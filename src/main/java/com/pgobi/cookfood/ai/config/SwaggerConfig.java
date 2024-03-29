package com.pgobi.cookfood.ai.config;

import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI().info(apiInfo())
                .addSecurityItem(
                        new SecurityRequirement().addList(ApplicationConstants.AUTHORIZATION_BEARER)).components(
                        new Components().addSecuritySchemes(ApplicationConstants.AUTHORIZATION_BEARER,
                                new SecurityScheme()
                                        .name(ApplicationConstants.AUTHORIZATION_BEARER)
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat("JWT")
                                        .scheme("bearer")));
    }

    public Info apiInfo() {
        Info info = new Info();
        info
                .title("CookFoodAI ")
                .description("CookFoodAI  API")
                .version("v1.0.0");
        return info;
    }

}
