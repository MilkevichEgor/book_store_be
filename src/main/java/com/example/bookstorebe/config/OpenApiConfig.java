package com.example.bookstorebe.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI.
 */
@Configuration
public class OpenApiConfig {

  /**
   * Returns a custom OpenAPI object.
   * This function sets up the security schemes, info, and security items for the OpenAPI.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .components(new Components().addSecuritySchemes("bearer-jwt",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP)
                            .scheme("bearer").bearerFormat("JWT")
                            .in(SecurityScheme.In.HEADER).name("Authorization")))
            .info(new Info().title("App API").version("snapshot"))
            .addSecurityItem(
                    new SecurityRequirement()
                            .addList("bearer-jwt", Arrays.asList("read", "write")));
  }

}
