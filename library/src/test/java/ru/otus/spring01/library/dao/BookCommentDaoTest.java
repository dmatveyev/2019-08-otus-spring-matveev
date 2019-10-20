package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.dao.impl.BookCommentDaoImpl;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.BookComment;
import ru.otus.spring01.library.domain.Person;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = DaoConfiguration.class)
@Import(BookCommentDaoImpl.class)
class BookCommentDaoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookCommentDao bookCommentDao;

    @Autowired
    private ISBNGenerator isbnGenerator;

    private Book book;
    private Person person;
    private BookComment bookComment;

    @BeforeEach
    void setUp() {
        book = new Book(UUID.randomUUID(), "test", isbnGenerator.generateNumber());
        person = new Person(UUID.randomUUID(), "admin");
        person.setPassword("admin");
        bookComment = new BookComment();
        bookComment.setBook(book);
        bookComment.setPerson(person);
        bookComment.setComment("testcomment");
        testEntityManager.persist(book);
        testEntityManager.persist(person);
        testEntityManager.persist(bookComment);
    }

    @AfterEach
    void tearDown() {
        testEntityManager.remove(book);
        testEntityManager.remove(person);
        testEntityManager.remove(bookComment);
    }

    @Test
    void getAll() {
        List<BookComment> all = bookCommentDao.getAll();
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
        bookCommentDao.insertComment(newComment);
        List<BookComment> all = bookCommentDao.getAll();
        boolean contains = all.contains(bookComment);
        assertTrue(contains);
    }

    @Test
    void updateComment() {
        String newcomment = "newcomment";
        bookCommentDao.updateComment(bookComment.getId(), newcomment);
        BookComment updatedBook = testEntityManager.find(BookComment.class, bookComment.getId());
        assertEquals(newcomment, updatedBook.getComment());
    }

    @Test
    void deleteComment() {
        bookCommentDao.deleteComment(bookComment.getId());
        List<BookComment> all = bookCommentDao.getAll();
        assertEquals(0, all.size());
    }
}