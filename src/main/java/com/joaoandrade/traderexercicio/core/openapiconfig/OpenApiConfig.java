package com.joaoandrade.traderexercicio.core.openapiconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Trader API").description(
				"API criada com o objetivo de ter um back-end de teste para poder treinar o front-end utilizando o VueJs ou o Flutter")
				.version("1.0").contact(new Contact().email("famosajinx@gmail.com")));
	}
}
