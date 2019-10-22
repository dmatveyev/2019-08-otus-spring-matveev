package ru.otus.spring01.library.dao;

import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.library.service.ISBNGenerator;

@TestConfiguration
@ActiveProfiles("test")
public class DaoConfiguration {

    @Bean
    public ISBNGenerator isbnGenerator() {
        return new ISBNGenerator();
    }
}
