package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring01.library.dao.impl.AuthorDaoImpl;
import ru.otus.spring01.library.domain.Author;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Author Dao")
@Import(AuthorDaoImpl.class)
class AuthorDaoTest {

    private static final String FIRST_TEST_NAME = "Denis";
    private static final String SECOND_TEST_NAME = "Matveev";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuthorDao authorDao;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author();
        author1.setName(FIRST_TEST_NAME);
        author2 = new Author();
        author2.setName(SECOND_TEST_NAME);
        testEntityManager.persist(author1);
        testEntityManager.persist(author2);
    }

    @AfterEach
    void tearDown() {
        testEntityManager.remove(author1);
        testEntityManager.remove(author2);
    }

    @Test
    @DisplayName("Checks count method")
    void count() {
        Long count = authorDao.count();
        assertEquals(Long.valueOf(2), count);
    }

    @Test
    @DisplayName("Checks getById method")
    void getById() {
        UUID id = author1.getId();
        Author byId = authorDao.getById(id);
        assertNotNull(byId);
        assertEquals(id, byId.getId());
    }

    @Test
    @DisplayName("Checks getAll method")
    void getAll() {
        List<Author> all = authorDao.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("Checks deleting Author without books")
    void delete() {
        UUID id = author1.getId();
        authorDao.deleteById(id);
        assertNull(authorDao.getById(id));
        assertEquals(Long.valueOf(1), authorDao.count());
    }

    @Test
    @DisplayName("Checks contains method")
    void contains() {
        Author author = new Author();
        author.setName(FIRST_TEST_NAME);
        boolean contains = authorDao.contains(author);
        assertTrue(contains);
    }

    @Test
    @DisplayName("Checks that duplicate author won't be inserted")
    void nonInsertDuplicate() {
        Author author = new Author();
        author.setName(FIRST_TEST_NAME);
        authorDao.insert(author);
        Author byId = authorDao.getById(author1.getId());
        assertNotNull(byId);
        assertEquals(Long.valueOf(2), authorDao.count());
    }
}