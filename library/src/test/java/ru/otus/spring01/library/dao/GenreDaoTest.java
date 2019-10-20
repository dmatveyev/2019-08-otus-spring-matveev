package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.dao.impl.GenreDaoImpl;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = DaoConfiguration.class)
@DisplayName("Tests for Genre Dao")
@Import(GenreDaoImpl.class)
class GenreDaoTest {

    private static final String FIRST_TEST_NAME = "fantasy";
    private static final String FIRST_TEST_CODE = "0001";
    private static final String SECOND_TEST_NAME = "action";
    private static final String SECOND_TEST_CODE = "00002";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GenreDao genreDao;

    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void setUp() {
        genre1 = new Genre();
        genre1.setName(FIRST_TEST_NAME);
        genre1.setCode(FIRST_TEST_CODE);
        genre2 = new Genre();
        genre2.setName(SECOND_TEST_NAME);
        genre2.setCode(SECOND_TEST_CODE);
        testEntityManager.persist(genre1);
        testEntityManager.persist(genre2);
    }

    @AfterEach
    void tearDown() {
        testEntityManager.remove(genre1);
        testEntityManager.remove(genre2);
    }

    @Test
    @DisplayName("Check count method")
    void count() {
        Long count = genreDao.count();
        assertEquals(Long.valueOf(4), count);
    }

    @Test
    @DisplayName("Check getById method")
    void getById() {
        UUID id = genre1.getId();
        Genre byId = genreDao.getById(id);
        assertNotNull(byId);
        assertEquals(id, byId.getId());
    }

    @Test
    @DisplayName("Check getAll method")
    void getAll() {
        List<Genre> all = genreDao.getAll();
        assertNotNull(all);
        assertEquals(4, all.size());
    }

    @Test
    @DisplayName("Deleting Genre without books")
    void deleteWithoutBooks() {
        UUID id = genre1.getId();
        genreDao.deleteById(id);
        assertNull(genreDao.getById(id));
        assertEquals(Long.valueOf(3), genreDao.count());
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
    void nonInsertDuplicate() {
        Genre genre = new Genre();
        genre.setName(FIRST_TEST_NAME);
        genre.setCode(FIRST_TEST_CODE);
        genreDao.insert(genre);
        Genre byId = genreDao.getById(genre1.getId());
        assertNotNull(byId);
        assertEquals(Long.valueOf(4), genreDao.count());
    }

    @Test
    @DisplayName("Checks getByName")
    void getByName() {
        Genre byName = genreDao.getByName(genre1.getName());
        assertEquals(genre1.getId(), byName.getId());
    }

}