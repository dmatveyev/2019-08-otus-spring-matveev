package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.*;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = DaoConfiguration.class)
class BookCommentDaoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookCommentDao bookCommentDao;

    @Autowired
    private ISBNGenerator isbnGenerator;

    private Author author;
    private Genre genre;
    private Book book;
    private Person person;
    private BookComment bookComment;

    @BeforeEach
    @Rollback
    void setUp() {
        author = new Author();
        author.setName("TestAuthor");

        genre = new Genre();
        genre.setName("detective");
        genre.setCode("007");
        book = new Book(UUID.randomUUID(), "test", isbnGenerator.generateNumber());
        book.setGenre(genre);
        book.setAuthor(author);
        person = new Person(UUID.randomUUID(), "admin");
        person.setPassword("admin");
        bookComment = new BookComment();
        bookComment.setBook(book);
        bookComment.setPerson(person);
        bookComment.setComment("testcomment");
        testEntityManager.persist(genre);
        testEntityManager.persist(author);
        testEntityManager.persist(book);
        testEntityManager.persist(person);
        testEntityManager.persist(bookComment);
    }

    @Test
    void getAll() {
        List<BookComment> all = bookCommentDao.findAll();
        assertNotNull(all);
        assertEquals(1, all.size());
    }

    @Test
    void getBookCommentsByPerson() {
        List<BookComment> bookCommentsByPerson = bookCommentDao.getBookCommentsByPerson(person);
        assertNotNull(bookCommentsByPerson);
        assertEquals(1, bookCommentsByPerson.size());

    }

    @Test
    void getBookCommentsByBook() {
        List<BookComment> bookCommentsByBook = bookCommentDao.getBookCommentsByBook(book);
        assertNotNull(bookCommentsByBook);
        assertEquals(1, bookCommentsByBook.size());
    }

    @Test
    void insertComment() {
        BookComment newComment = new BookComment();
        newComment.setComment("");
        newComment.setPerson(person);
        newComment.setBook(book);
        bookCommentDao.save(newComment);
        List<BookComment> all = bookCommentDao.findAll();
        boolean contains = all.contains(bookComment);
        assertTrue(contains);
    }

    @Test
    void updateComment() {
        String newcomment = "newcomment";
        bookComment.setComment(newcomment);
        bookCommentDao.save(bookComment);
        BookComment updatedBook = testEntityManager.find(BookComment.class, bookComment.getId());
        assertEquals(newcomment, updatedBook.getComment());
        assertEquals(1, bookCommentDao.count());
    }

    @Test
    void deleteComment() {
        bookCommentDao.deleteCommentById(bookComment.getId());
        List<BookComment> all = bookCommentDao.findAll();
        assertEquals(0, all.size());
    }
}