package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring01.library.domain.Author;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.spring01.library.dao.TestConstants.*;

@DataMongoTest
@DisplayName("Tests for Author Dao")
class AuthorDaoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthorDao authorDao;
    private Author author1;
    private Author author2;

    @BeforeEach
    void init() {
        author1 = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        author2 = new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME);
        mongoTemplate.save(author1);
        mongoTemplate.save(author2);
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
        Author byId = authorDao.getById(FIRST_AUTHOR_ID);
        assertNotNull(byId);
        assertEquals(FIRST_AUTHOR_ID, byId.getId());
    }

    @Test
    @DisplayName("Checks getAll method")
    void getAll() {
        List<Author> all = authorDao.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

   @Test
    @DisplayName("Checks deleting Author without books")
    void delete() {
        Author author = new Author(UUID.randomUUID(), "testname");
        mongoTemplate.save(author);
        assertTrue(authorDao.existsByName(author.getName()));
        authorDao.deleteById(author.getId());
        assertNull(authorDao.getById(author.getId()));
        assertEquals(2, authorDao.count());
        mongoTemplate.remove(author);
    }

    @Test
    @DisplayName("Checks contains method")
    void contains() {
        boolean contains = authorDao.existsByName(FIRST_AUTHOR_NAME);
        assertTrue(contains);
    }

    @Test
    @DisplayName("Checks that duplicate author won't be inserted")
    void nonInsertDuplicate() {
        assertThrows(Exception.class, () -> {
            authorDao.insert(author1);
            Author byId = authorDao.getById(FIRST_AUTHOR_ID);
            assertNotNull(byId);
            assertEquals(2, authorDao.count());
      });
    }
}