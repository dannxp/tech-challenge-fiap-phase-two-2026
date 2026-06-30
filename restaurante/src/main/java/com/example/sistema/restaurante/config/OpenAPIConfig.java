package com.example.sistema.restaurante.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestão de Restaurantes")
                        .description("API para gestão compartilhada de restaurantes, com cadastro de tipos de usuário, usuários, restaurantes e itens de cardápio.")
                        .version("1.0.0"));
    }
}
