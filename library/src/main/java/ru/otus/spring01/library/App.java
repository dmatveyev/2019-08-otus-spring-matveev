package ru.otus.spring01.library;

import lombok.SneakyThrows;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class App {

    @SneakyThrows
    public static void main(String[] args) {

        SpringApplication.run(App.class);

        Console.main(args);
    }
}
