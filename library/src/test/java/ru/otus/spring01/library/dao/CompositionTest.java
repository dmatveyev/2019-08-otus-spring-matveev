package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.service.ISBNGenerator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ContextConfiguration(classes = DaoConfiguration.class)
public class CompositionTest {

    private static Author author;
    private static Genre genre;
    private static Book book;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private ISBNGenerator isbnGenerator;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeAll
    static void createDate() {
        author = new Author();
        author.setName("TestAuthor");

        genre = new Genre();
        genre.setName("detective");
        genre.setCode("007");

        book = new Book();
        book.setAuthor(author);
        book.setGenre(genre);
        book.setName("Cool detective");

    }

    @BeforeEach
    @Rollback
    void setUp() {
        book.setIsbn(isbnGenerator.generateNumber());
        mongoTemplate.save(author);
        mongoTemplate.save(genre);
        mongoTemplate.save(book);
    }

    @Test
    void deletingGenreUsedInBooks() {
        assertThrows(Exception.class, () -> {
            genreDao.deleteById(genre.getId());
            assertTrue(genreDao.existsById(genre.getId()));
        });
    }

    @Test
    void deletingAuthorUsedInBooks() {
        assertThrows(Exception.class, () -> {
            authorDao.deleteById(author.getId());
            assertTrue(authorDao.existsById(author.getId()));
        });
    }
}
