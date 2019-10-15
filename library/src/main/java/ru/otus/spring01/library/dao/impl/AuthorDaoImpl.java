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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject(
                "select count(*) from authors", Collections.emptyMap(), Integer.class);
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
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select * from authors a " +
                " left join books b on a.id = b.author_id ", new AuthorMapper());
    }

    @Override
    public void deleteById(UUID authorId) {
        Map<String, Object> params = Collections.singletonMap("authorId", authorId);
        Integer count = namedParameterJdbcOperations.queryForObject("select count(*) from books where author_id = :authorId",
                params, Integer.class);
        if (count > 0) {
            throw new AuthorHasBookException("Author has books, which is user");
        }
        namedParameterJdbcOperations.update(
                "delete from authors where id = :authorId", params
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

    @Override
    public Author getByName(String name) {
        Map<String, String> param = Collections.singletonMap("name", name);

        List<Author> result = namedParameterJdbcOperations.query("select * from authors where `name` = :name ",
                param,
                new AuthorMapper());

        return result.isEmpty() ? null : result.get(0);
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
