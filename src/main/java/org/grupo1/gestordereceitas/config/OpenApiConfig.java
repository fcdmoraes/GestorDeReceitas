

package org.grupo1.gestordereceitas.config;

import io.swagger.v3.oas.models.OpenAPI; // Correto
import io.swagger.v3.oas.models.info.Info; // Correto
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gestaoReceitasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestor de Receitas")
                        .description("Documentação da API do sistema de gestão de receitas e ingredientes")
                        .version("1.0.0"));
    }
}