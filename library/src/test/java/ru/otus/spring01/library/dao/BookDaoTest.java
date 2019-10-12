package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ContextConfiguration(classes = DaoConfiguration.class)
class BookDaoTest {

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ISBNGenerator isbnGenerator;

    private static Author author;
    private static Genre genre1;
    private static Genre genre2;
    private static Book book1;
    private static Book book2;
    private static List<Book> bookList;

    @BeforeAll
    static void createData() {
        author = new Author();
        author.setName("TestAuthor");
        genre1 = new Genre();
        genre1.setName("Action");
        genre1.setCode("0001");
        genre2 = new Genre();
        genre2.setName("Fantasy");
        genre2.setCode("0002");

        book1 = new Book();
        book1.setAuthor(author);
        book1.setGenre(genre1);
        book1.setName("Some action");

        book2 = new Book();
        book2.setName("Some fantasy");
        book2.setGenre(genre2);
        book2.setAuthor(author);

        bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
    }

    @BeforeEach
    void setUp() {
        book1.setIsbn(isbnGenerator.generateNumber());
        book2.setIsbn(isbnGenerator.generateNumber());

        namedParameterJdbcOperations.update("insert into genres (id, `name`, code) " +
                "values(:id, :name, :code)", new BeanPropertySqlParameterSource(genre1));
        namedParameterJdbcOperations.update("insert into genres (id, `name`, code) " +
                "values(:id, :name, :code)", new BeanPropertySqlParameterSource(genre2));
        namedParameterJdbcOperations.update("insert into authors (id, `name`) " +
                "values(:id, :name, )", new BeanPropertySqlParameterSource(author));
        bookList.forEach(book -> {
            Map<String, Object> params = new HashMap<>();
            params.put("id", book.getId());
            params.put("name", book.getName());
            params.put("isbn", book.getIsbn());
            params.put("genreId", book.getGenre().getId());
            params.put("authorId", book.getAuthor().getId());
            namedParameterJdbcOperations.update("insert into books (id, `name`, genre_id, isbn, author_id) " +
                    "values(:id, :name, :genreId, :isbn, :authorId)", params);
        });
    }

    @AfterEach
    void tearDown() {
        namedParameterJdbcOperations.update("delete from books where id is not null", Collections.emptyMap());
        namedParameterJdbcOperations.update("delete from genres where id is not null", Collections.emptyMap());
        namedParameterJdbcOperations.update("delete from authors where id is not null", Collections.emptyMap());
    }

    @Test
    void count() {
        int count = bookDao.count();
        assertEquals(bookList.size(), count);
    }

    @Test
    void insert() {
        Book newBook = new Book();
        newBook.setId(UUID.randomUUID());
        newBook.setName(book1.getName());
        newBook.setIsbn(book1.getIsbn());
        newBook.setGenre(book1.getGenre());
        newBook.setAuthor(book1.getAuthor());
        bookDao.insert(newBook);
        assertEquals(bookList.size(), bookDao.count());

    }

    @Test
    void getById() {
        Book byId = bookDao.getById(book1.getId());
        assertEquals(book1.getIsbn(), byId.getIsbn());
        assertEquals(book1.getAuthor().getName(), byId.getAuthor().getName());
        assertEquals(book1.getGenre().getName(), byId.getGenre().getName());

        Book nullable = bookDao.getById(UUID.randomUUID());
        assertNull(nullable);

    }

    @Test
    void getAll() {
        List<Book> all = bookDao.getAll();
        assertEquals(bookList.size(), all.size());
    }

    @Test
    void getByISBN() {
        Book byISBN = bookDao.getByISBN(book1.getIsbn());
        assertEquals(book1.getId(), byISBN.getId());
        Book byISBN1 = bookDao.getByISBN("333");
        assertNull(byISBN1);
    }

    @Test
    void getByNameLike() {
        List<Book> result = bookDao.getByNameLike("some");
        assertEquals(bookList.size(), result.size());
    }

    @Test
    void getByGenre() {
        List<Book> byGenre = bookDao.getByGenre(genre1);
        assertEquals(1, byGenre.size());
        Genre fakeGenre = new Genre();
        List<Book> byGenre1 = bookDao.getByGenre(fakeGenre);
        assertEquals(0, byGenre1.size());
    }

    @Test
    void getByAuthorName() {
        String name = book1.getAuthor().getName();
        List<Book> byAuthorName = bookDao.getByAuthorName(name);
        assertNotNull(byAuthorName);
        assertEquals(bookList.size(), byAuthorName.size());
    }

    @Test
    void deleteById() {
        bookDao.deleteById(book1.getId());
        boolean contains = bookDao.contains(book1);
        assertFalse(contains);
        int count = bookDao.count();
        assertEquals(bookList.size() - 1, count);
    }

    @Test
    void containsBook() {
        boolean contains = bookDao.contains(book1);
        assertTrue(contains);
    }
}