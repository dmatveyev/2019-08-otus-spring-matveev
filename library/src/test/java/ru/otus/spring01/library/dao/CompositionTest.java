package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.exception.AuthorHasBookException;
import ru.otus.spring01.library.exception.GenreHasBookException;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJdbcTest
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
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

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
    void setUp() {
        book.setIsbn(isbnGenerator.generateNumber());
        namedParameterJdbcOperations.update("insert into authors (id, `name`) " +
                "values (:id, :name)", new BeanPropertySqlParameterSource(author));
        namedParameterJdbcOperations.update("insert into genres (id, `name`, code) " +
                "values (:id, :name, :code)", new BeanPropertySqlParameterSource(genre));
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("name", book.getName());
        params.put("isbn", book.getIsbn());
        params.put("genreId", book.getGenre().getId());
        params.put("authorId", book.getAuthor().getId());
        namedParameterJdbcOperations.update("insert into books (id, `name`, isbn, author_id, genre_id) " +
                "values (:id, :name, :isbn, :authorId, :genreId)", params);

    }

    @AfterEach
    void tearDown() {
        namedParameterJdbcOperations.update("delete from books where id is not null", Collections.emptyMap());
        namedParameterJdbcOperations.update("delete from genres where id is not null", Collections.emptyMap());
        namedParameterJdbcOperations.update("delete from authors where id is not null", Collections.emptyMap());
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
