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
import ru.otus.spring01.library.domain.Genre;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ContextConfiguration(classes = DaoConfiguration.class)
class GenreDaoTest {

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    private GenreDao genreDao;

    private static final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        Genre genre1 = new Genre();
        genre1.setId(id);
        genre1.setName("fantasy");
        genre1.setCode("0001");
        Genre genre2 = new Genre();
        genre2.setName("action");
        genre2.setCode("00002");
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
    @DisplayName("Deleting Author without books")
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

}