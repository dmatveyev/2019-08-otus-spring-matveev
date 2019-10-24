package ru.otus.spring01.library.dao;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.otus.spring01.library.service.ISBNGenerator;

@TestConfiguration
public class DaoConfiguration {

    @Bean
    public ISBNGenerator isbnGenerator() {
        return new ISBNGenerator();
    }
}
