package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.*;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.UUID;

@DataMongoTest
@ContextConfiguration(classes = DaoConfiguration.class)
@DisplayName("Tests for Book Dao")
@ComponentScan("ru.otus.spring01.library.cascade")
public class CascadeTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BookCommentDao bookCommentDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private ISBNGenerator isbnGenerator;

    private Author author;
    private Genre genre;
    private Book book;
    private Person person;
    private BookComment bookComment;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setName("TestAuthor");

        genre = new Genre();
        genre.setName("detective");
        genre.setCode("007");
        book = new Book(UUID.randomUUID().toString(), "test", isbnGenerator.generateNumber());
        book.setGenre(genre);
        book.setAuthor(author);
        person = new Person(UUID.randomUUID().toString(), "admin");
        person.setPassword("admin");
        bookComment = new BookComment();
        bookComment.setBook(book);
        bookComment.setPerson(person);
        bookComment.setComment("testcomment");
        mongoTemplate.save(genre);
        mongoTemplate.save(author);
        mongoTemplate.save(book);
        mongoTemplate.save(person);
        mongoTemplate.save(bookComment);
    }

    @Test
    void cascadeDeletionBookComments() {
        long count1 = bookCommentDao.count();
        Assertions.assertEquals(1L, count1);
        bookDao.delete(book);
        long count2 = bookCommentDao.count();
        Assertions.assertEquals(0L, count2);
    }
}
