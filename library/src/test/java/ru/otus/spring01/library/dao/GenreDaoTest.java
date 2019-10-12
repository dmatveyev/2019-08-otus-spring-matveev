package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ContextConfiguration(classes = DaoConfiguration.class)
@DisplayName("Tests for Genre Dao")
class GenreDaoTest {

    public static final String FIRST_TEST_NAME = "fantasy";
    public static final String FIRST_TEST_CODE = "0001";
    public static final String SECOND_TEST_NAME = "action";
    public static final String SECOND_TEST_CODE = "00002";
    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    private GenreDao genreDao;

    private static final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        Genre genre1 = new Genre();
        genre1.setId(id);
        genre1.setName(FIRST_TEST_NAME);
        genre1.setCode(FIRST_TEST_CODE);
        Genre genre2 = new Genre();
        genre2.setName(SECOND_TEST_NAME);
        genre2.setCode(SECOND_TEST_CODE);
        namedParameterJdbcOperations.update("insert into genres (id, `name`, code) values (:id, :name, :code)",
                new BeanPropertySqlParameterSource(genre1));
        namedParameterJdbcOperations.update("insert into genres (id, `name`, code) values (:id, :name, :code)",
                new BeanPropertySqlParameterSource(genre2));
    }

    @Test
    @DisplayName("Check count method")
    void count() {
        int count = genreDao.count();
        assertEquals(2, count);
    }

    @Test
    @DisplayName("Check getById method")
    void getById() {
        Genre byId = genreDao.getById(id);
        assertNotNull(byId);
        assertEquals(id, byId.getId());
    }

    @Test
    @DisplayName("Check getAll method")
    void getAll() {
        List<Genre> all = genreDao.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("Deleting Genre without books")
    void deleteWithoutBooks() {
        UUID id = UUID.randomUUID();
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("Test");
        genreDao.insert(genre);
        Genre byId = genreDao.getById(id);
        assertNotNull(byId);
        genreDao.deleteById(id);
        assertNull(genreDao.getById(id));
    }

    @Test
    @DisplayName("Checks contains method")
    void contains() {
        Genre genre = new Genre();
        genre.setName(FIRST_TEST_NAME);
        genre.setCode(FIRST_TEST_CODE);
        boolean contains = genreDao.contains(genre);
        assertTrue(contains);
    }

    @Test
    @DisplayName("Checks that duplicate genre won't be inserted")
    void nonInsertDuplicate(){
        Genre genre = new Genre();
        genre.setName(FIRST_TEST_NAME);
        genre.setCode(FIRST_TEST_CODE);
        genreDao.insert(genre);
        Genre byId = genreDao.getById(id);
        assertNotNull(byId);
        assertEquals(2, genreDao.count());
    }

}