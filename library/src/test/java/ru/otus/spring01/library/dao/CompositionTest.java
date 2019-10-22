package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.dao.impl.AuthorDaoImpl;
import ru.otus.spring01.library.dao.impl.BookDaoImpl;
import ru.otus.spring01.library.dao.impl.GenreDaoImpl;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.exception.AuthorHasBookException;
import ru.otus.spring01.library.exception.GenreHasBookException;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = DaoConfiguration.class)
@Import({AuthorDaoImpl.class, BookDaoImpl.class, GenreDaoImpl.class})
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
    private TestEntityManager testEntityManager;

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

        author.setBooks(Collections.singletonList(book));
    }

    @BeforeEach
    @Rollback
    void setUp() {
        book.setIsbn(isbnGenerator.generateNumber());
        testEntityManager.persist(author);
        testEntityManager.persist(genre);
        testEntityManager.persist(book);
    }

    @Test
    void deletingGenreUsedInBooks() {
        assertThrows(GenreHasBookException.class, () -> genreDao.deleteById(genre.getId()));
    }

    @Test
    void deletingAuthorUsedInBooks() {
        assertThrows(AuthorHasBookException.class, () -> authorDao.deleteById(author.getId()));
    }
}
