package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.*;
import ru.otus.spring01.library.service.ISBNGenerator;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
@ContextConfiguration(classes = DaoConfiguration.class)
class BookCommentDaoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

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
        mongoTemplate.save(genre);
        mongoTemplate.save(author);
        mongoTemplate.save(book);
        mongoTemplate.save(person);
        mongoTemplate.save(bookComment);
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.remove(bookComment);
        mongoTemplate.remove(person);
        mongoTemplate.remove(book);
        mongoTemplate.remove(genre);
        mongoTemplate.remove(author);
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
        BookComment updatedBook = mongoTemplate.findById(bookComment.getId(), BookComment.class );
        assertEquals(newcomment, updatedBook.getComment());
        assertEquals(1, bookCommentDao.count());
    }

    @Test
    void deleteComment() {
        bookCommentDao.deleteById(bookComment.getId());
        assertNull(bookCommentDao.getById(bookComment.getId()));

    }

    @Test
    void getCountCommentsByBook() {
        long count = bookCommentDao.countBookCommentByBookId(book.getId());
        assertEquals(1L, count);
    }

    @Test
    void deleteBookCommentsByBookId() {
        bookCommentDao.deleteByBookId(book.getId());
        long count = bookCommentDao.countBookCommentByBookId(book.getId());
        assertEquals(0L, count);
    }
}