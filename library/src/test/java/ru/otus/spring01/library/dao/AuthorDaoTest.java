package ru.otus.spring01.library.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring01.library.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJdbcTest
@ContextConfiguration(classes = DaoConfiguration.class)
class AuthorDaoTest {

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    private AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        Author author1 = new Author();
        author1.setName("Denis");
        Author author2 = new Author();
        author2.setName("Matveev");
        namedParameterJdbcOperations.update("insert into authors (id, `name`) values (:id, :name)",
                new BeanPropertySqlParameterSource(author1));
        namedParameterJdbcOperations.update("insert into authors (id, `name`) values (:id, :name)",
                new BeanPropertySqlParameterSource(author2));
    }

    @Test
    @DisplayName("Check count method")
    void count() {
        int count = authorDao.count();
        assertEquals(2, count);
    }

    @Test
    void getById() {

    }

    @Test
    @DisplayName("Check getAll method")
    void getAll() {
        List<Author> all = authorDao.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());
    }

}