package ru.otus.spring01.library;

import lombok.SneakyThrows;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring01.library.dao.PersonDao;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Person;

import java.util.UUID;

@SpringBootApplication
public class App {

    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class);

        PersonDao dao = context.getBean(PersonDao.class);

        System.out.println("All count " + dao.count());

        UUID id = UUID.randomUUID();
        dao.insert(new Person(id, "ivan"));

        System.out.println("All count " + dao.count());

        Person ivan = dao.getById(id);

        System.out.println("Ivan id: " + ivan.getId() + " name: " + ivan.getName());

        Book book = new Book();

        Console.main(args);
    }
}
