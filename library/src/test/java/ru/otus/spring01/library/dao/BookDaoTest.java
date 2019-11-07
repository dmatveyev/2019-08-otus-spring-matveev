package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.spring01.library.dao.TestConstants.*;

@DataJpaTest
@ContextConfiguration(classes = DaoConfiguration.class)
@DisplayName("Tests for Book Dao")
class BookDaoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ISBNGenerator isbnGenerator;


    @Test
    @DisplayName("Checks count method")
    void count() {
        Long count = bookDao.count();
        assertEquals(Long.valueOf(2), count);
    }

    @Test
    @DisplayName("Checks insertion")
    void insert() {
        Genre genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME, FIRST_GENRE_CODE);
        Author author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        Book newBook = new Book(UUID.randomUUID(), FIRST_BOOK_NAME, FIRST_BOOK_ISBN);
        newBook.setGenre(genre);
        newBook.setAuthor(author);
        bookDao.save(newBook);
        assertEquals(3, bookDao.count());

    }

    @Test
    @DisplayName("Checks getting by id")
    void getById() {
        Book byId = bookDao.getById(FIRST_BOOK_ID);
        assertEquals(FIRST_BOOK_ISBN, byId.getIsbn());
        assertEquals(FIRST_AUTHOR_NAME, byId.getAuthor().getName());
        assertEquals(SECOND_GENRE_NAME, byId.getGenre().getName());

        Book nullable = bookDao.getById(UUID.randomUUID());
        assertNull(nullable);

    }

    @Test
    @DisplayName("Checks getting all books")
    void getAll() {
        List<Book> all = bookDao.findAll();
        assertEquals(2, all.size());
        all.forEach(book -> {
            assertNotNull(book.getGenre());
            assertNotNull(book.getAuthor());
        });
    }

    @Test
    @DisplayName("Checks get book by isbn")
    void getByISBN() {
        Book byISBN = bookDao.getByIsbn(FIRST_BOOK_ISBN);
        assertEquals(FIRST_BOOK_ID, byISBN.getId());
        Book byISBN1 = bookDao.getByIsbn("333");
        assertNull(byISBN1);
    }

    @Test
    @DisplayName("Checks getting books by name")
    void getByNameLike() {
        List<Book> result = bookDao.getByNameContainingIgnoreCase("some");
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Checks getting books by genre")
    void getByGenre() {
        Genre genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME, FIRST_GENRE_CODE);
        List<Book> byGenre = bookDao.getByGenre(genre);
        assertEquals(1, byGenre.size());
        Genre fakeGenre = new Genre();
        List<Book> byGenre1 = bookDao.getByGenre(fakeGenre);
        assertEquals(0, byGenre1.size());
    }

    @Test
    @DisplayName("Checks getting books by author name")
    void getByAuthorName() {
        List<Book> byAuthorName = bookDao.getByAuthorName(FIRST_AUTHOR_NAME);
        assertNotNull(byAuthorName);
        assertEquals(2, byAuthorName.size());
    }

    @Test
    @DisplayName("Checks deletion book by id")
    void deleteById() {
        Genre genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME, FIRST_GENRE_CODE);
        Author author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        Book newBook = new Book(UUID.randomUUID(), FIRST_BOOK_NAME, FIRST_BOOK_ISBN);
        newBook.setGenre(genre);
        newBook.setAuthor(author);
        testEntityManager.persist(newBook);
        assertEquals(3, bookDao.count());
        bookDao.deleteById(newBook.getId());
        boolean contains = bookDao.existsByNameAndGenreNameAndAuthorName(newBook.getName(),
                newBook.getGenre().getName(),
                newBook.getAuthor().getName() );
        assertFalse(contains);
        Long count = bookDao.count();
        assertEquals(Long.valueOf(2), count);
    }

    @Test
    @DisplayName("Checks contains method")
    void containsBook() {
        Genre genre = new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME, SECOND_GENRE_CODE);
        Author author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        Book newBook = new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME, FIRST_BOOK_ISBN);
        newBook.setGenre(genre);
        newBook.setAuthor(author);
        boolean contains = bookDao.existsByNameAndGenreNameAndAuthorName(newBook.getName(),
                newBook.getGenre().getName(),
                newBook.getAuthor().getName());
        assertTrue(contains);
        Book anotherBook = new Book(UUID.randomUUID(),"123", "123");
        anotherBook.setAuthor(author);
        anotherBook.setGenre(genre);
        assertFalse(bookDao.existsByNameAndGenreNameAndAuthorName(anotherBook.getName(),
                anotherBook.getGenre().getName(),
                anotherBook.getAuthor().getName()));
    }
}