package ru.otus.spring01.library.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-test.yaml")
@ComponentScan("ru.otus.spring01.library.dao")
public class DaoConfiguration {
}
