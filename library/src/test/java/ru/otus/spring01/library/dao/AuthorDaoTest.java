package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.Author;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ContextConfiguration(classes = DaoConfiguration.class)
@DisplayName("Tests for Author Dao")
class AuthorDaoTest {

    private static final String FIRST_TEST_NAME = "Denis";
    private static final String SECOND_TEST_NAME = "Matveev";
    private static final UUID id = UUID.randomUUID();

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    private AuthorDao authorDao;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author();
        author1.setName(FIRST_TEST_NAME);
        author1.setId(id);
        author2 = new Author();
        author2.setName(SECOND_TEST_NAME);
        namedParameterJdbcOperations.update("insert into authors (id, `name`) values (:id, :name)",
                new BeanPropertySqlParameterSource(author1));
        namedParameterJdbcOperations.update("insert into authors (id, `name`) values (:id, :name)",
                new BeanPropertySqlParameterSource(author2));
    }

    @AfterEach
    void tearDown() {
        namedParameterJdbcOperations.update("delete from authors where id is not null ",
                Collections.emptyMap());
    }

    @Test
    @DisplayName("Checks count method")
    void count() {
        int count = authorDao.count();
        assertEquals(2, count);
    }

    @Test
    @DisplayName("Checks getById method")
    void getById() {
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
        authorDao.deleteById(author1.getId());
        assertNull(authorDao.getById(id));
        assertEquals(1, authorDao.count());
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
        Author byId = authorDao.getById(id);
        assertNotNull(byId);
        assertEquals(2, authorDao.count());
    }
}