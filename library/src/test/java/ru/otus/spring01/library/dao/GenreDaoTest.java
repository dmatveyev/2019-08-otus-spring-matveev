package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = DaoConfiguration.class)
@DisplayName("Tests for Genre Dao")
class GenreDaoTest {

    private static final UUID FIRST_ID = UUID.fromString("1cc9b82f-b3d5-4b1b-a3f9-eafc32dd6fa9");
    private static final String FIRST_TEST_NAME = "fantasy";
    private static final String FIRST_TEST_CODE = "00001";
    private static final UUID SECOND_ID = UUID.fromString("67953367-7369-4458-b14c-1456a1d345a2");
    private static final String SECOND_TEST_NAME = "action";
    private static final String SECOND_TEST_CODE = "00002";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GenreDao genreDao;


    @Test
    @DisplayName("Check count method")
    void count() {
        Long count = genreDao.count();
        assertEquals(Long.valueOf(2), count);
    }

    @Test
    @DisplayName("Check getById method")
    void getById() {
        Genre byId = genreDao.getById(FIRST_ID);
        assertNotNull(byId);
        assertEquals(FIRST_ID, byId.getId());
    }

    @Test
    @DisplayName("Check getAll method")
    void getAll() {
        List<Genre> all = genreDao.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("Deleting Genre without books")
    void deleteWithoutBooks() {
        Genre genre = new Genre();
        genre.setName(FIRST_TEST_NAME + "1");
        genre.setCode(FIRST_TEST_CODE + "1");
        testEntityManager.persist(genre);
        assertTrue(genreDao.existsById(genre.getId()));
        assertEquals(3, genreDao.count());
        genreDao.deleteById(genre.getId());
        assertNull(genreDao.getById(genre.getId()));
        assertEquals(2, genreDao.count());
    }

    @Test
    @DisplayName("Checks contains method")
    void contains() {
        Genre genre = new Genre();
        genre.setName(FIRST_TEST_NAME);
        genre.setCode(FIRST_TEST_CODE);
        boolean contains = genreDao.existsByNameAndCode(genre.getName(), genre.getCode());
        assertTrue(contains);
    }

    @Test
    @DisplayName("Checks that duplicate genre won't be inserted")
    void nonInsertDuplicate() {
        Genre genre = new Genre();
        genre.setName(FIRST_TEST_NAME);
        genre.setCode(FIRST_TEST_CODE);
        assertThrows(Exception.class, () -> {
            genreDao.save(genre);
            Genre byId = genreDao.getById(FIRST_ID);
            assertNotNull(byId);
            assertEquals(2, genreDao.count());
        });
    }

    @Test
    @DisplayName("Checks getByName")
    void getByName() {
        Genre byName = genreDao.getByName(FIRST_TEST_NAME);
        assertEquals(FIRST_ID, byName.getId());
    }

}