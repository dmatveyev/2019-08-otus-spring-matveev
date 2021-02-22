package ru.otus.spring01.library.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Component
@EnableWebFlux
public class CorsFilter implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:3001")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE")
                .maxAge(3600);
    }
}
