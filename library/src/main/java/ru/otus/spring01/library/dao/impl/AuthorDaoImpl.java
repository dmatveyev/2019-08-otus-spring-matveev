package ru.otus.spring01.library.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from authors", new HashMap<>(), Integer.class);
    }

    @Override
    public void insert(Author author) {
        if (contains(author)) {
            return;
        }
        if (author.getId() == null) {
            author.setId(UUID.randomUUID());
        }
        namedParameterJdbcOperations.update(
                "insert into authors (id, `name`) values (:id, :name)",
                new BeanPropertySqlParameterSource(author));
    }

    @Override
    public Author getById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Author> result = namedParameterJdbcOperations.query("select * from authors a " +
                        "where a.id = :id",
                params, new AuthorMapper());
        return result.isEmpty() ? null: result.get(0);
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select * from authors a " +
                " left join books b on a.id = b.author_id ", new AuthorMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
    }

    @Override
    public boolean contains(Author author) {
        Map<String, String> params = Collections.singletonMap("name", author.getName());
        Integer integer = namedParameterJdbcOperations.queryForObject("select count(*) from authors where name = :name",
                params,
                Integer.class);
        return integer > 0;
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            Author author = new Author();
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            author.setId(id);
            author.setName(name);
            return author;
        }
    }
}
