package ru.otus.spring01.library.dao;

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
import static ru.otus.spring01.library.dao.TestConstants.FIRST_AUTHOR_ID;
import static ru.otus.spring01.library.dao.TestConstants.FIRST_AUTHOR_NAME;

@DataJpaTest
@DisplayName("Tests for Author Dao")
@Import(AuthorDaoImpl.class)
class AuthorDaoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Checks count method")
    void count() {
        Long count = authorDao.count();
        assertEquals(Long.valueOf(2), count);
    }

    @Test
    @DisplayName("Checks getById method")
    void getById() {
        Author byId = authorDao.getById(FIRST_AUTHOR_ID);
        assertNotNull(byId);
        assertEquals(FIRST_AUTHOR_ID, byId.getId());
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
        Author author = new Author(UUID.randomUUID(), "testname");
        testEntityManager.persist(author);
        assertTrue(authorDao.contains(author));
        authorDao.deleteById(author.getId());
        assertNull(authorDao.getById(author.getId()));
        assertEquals(Long.valueOf(2), authorDao.count());
    }

    @Test
    @DisplayName("Checks contains method")
    void contains() {
        Author author = new Author();
        author.setName(FIRST_AUTHOR_NAME);
        boolean contains = authorDao.contains(author);
        assertTrue(contains);
    }

    @Test
    @DisplayName("Checks that duplicate author won't be inserted")
    void nonInsertDuplicate() {
        Author author = new Author();
        author.setName(FIRST_AUTHOR_NAME);
        authorDao.insert(author);
        Author byId = authorDao.getById(FIRST_AUTHOR_ID);
        assertNotNull(byId);
        assertEquals(Long.valueOf(2), authorDao.count());
    }
}