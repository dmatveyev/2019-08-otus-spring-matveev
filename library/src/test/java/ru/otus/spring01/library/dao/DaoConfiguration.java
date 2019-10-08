package ru.otus.spring01.library.dao;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.library.dao.impl.AuthorDaoImpl;

@Configuration
@PropertySource("classpath:application-test.yaml")
@ComponentScan("ru.otus.spring01.library.dao")
public class DaoConfiguration {
}
