package ru.otus.spring01.library;

import lombok.SneakyThrows;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.dao.PersonDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.domain.Person;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.UUID;

@SpringBootApplication
public class App {

    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class);

        PersonDao dao = context.getBean(PersonDao.class);
        ISBNGenerator isbnGenerator = context.getBean(ISBNGenerator.class);
        AuthorDao authorDao = context.getBean(AuthorDao.class);
        BookDao bookDao = context.getBean(BookDao.class);
        System.out.println("All count " + dao.count());
        UUID id = UUID.randomUUID();
        dao.insert(new Person(id, "ivan"));
        System.out.println("All count " + dao.count());
        Person ivan = dao.getById(id);
        System.out.println("Ivan id: " + ivan.getId() + " name: " + ivan.getName());

        Genre fantasy = new Genre();
        fantasy.setName("fantasy");
        fantasy.setCode("001");

        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("Boris Akunin");
        authorDao.insert(author);
        Book book = new Book();
        book.setName("Adventures");
        book.setAuthor(author);
        book.setGenre(fantasy);
        book.setIsbn(isbnGenerator.generateNumber());
        bookDao.insert(book);
        Book book1 = new Book();
        book1.setName("Adventures 2");
        book1.setAuthor(author);
        book1.setGenre(fantasy);
        book1.setIsbn(isbnGenerator.generateNumber());
        bookDao.insert(book1);
        System.out.println(bookDao.containsBook(book1));
        System.out.println(bookDao.getByGenre(fantasy));
/*        System.out.println(bookDao.getByISBN(book.getIsbn()));
        System.out.println(bookDao.getByNameLike("adve"));
        System.out.println(authorDao.getAll());*/
        Console.main(args);
    }
}
