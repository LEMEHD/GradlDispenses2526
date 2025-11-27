package org.isfce.pid.GradleDispenses2526.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
public class OpenApiConfig {

    static {
        // LA SOLUTION : On dit explicitement à Swagger d'ignorer totalement 
        // le type "Authentication" dans tous les contrôleurs.
        SpringDocUtils.getConfig().addRequestWrapperToIgnore(Authentication.class);
    }

    @Bean
    public OpenAPI projectOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Dispenses ISFCE")
                        .version("1.0")
                        .description("Documentation de l'API pour le projet de dispenses."));
    }
}