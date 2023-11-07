package com.challenge.modelo_de_datos.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // Reemplaza con el paquete base de tu aplicación
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
                //.host("localhost:8080");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Order Service API",
                "Order Service API Description",
                "1.0",
                "http://metapthorce.com",
                new Contact("metapthorce", "https://metapthorce.com", "apis@metapthorce.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }


}