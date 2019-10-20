package ru.otus.spring01.library.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.GenreDao;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.exception.GenreHasBookException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from GENRES",
                new HashMap<>(), Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        if (!contains(genre)) {
            namedParameterJdbcOperations.update("insert into genres " +
                            " (id, `name`, code) values (:id, :name, :code)",
                    new BeanPropertySqlParameterSource(genre));
        }
    }

    @Override
    public Genre getById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        List<Genre> result = namedParameterJdbcOperations.query("select * from genres where id = :id",
                params,
                new GenreMapper());
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select * from genres",
                new GenreMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("genreId", id);
        Integer booksByGenre = namedParameterJdbcOperations.queryForObject("select count(*) from books where genre_id = :genreId",
                params, Integer.class);
        if (booksByGenre > 0) {
            throw new GenreHasBookException("There are any books by this genre");
        }
        namedParameterJdbcOperations.update("delete from genres where id = :genreId", params);
    }

    @Override
    public Genre getByName(String name) {
        Map<String, String> param = Collections.singletonMap("name", name);

        List<Genre> result = namedParameterJdbcOperations.query("select * from genres where `name` = :name ",
                param,
                new GenreMapper());

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public boolean contains(Genre genre) {
        Map<String, String> params = new HashMap<>();
        params.put("name", genre.getName());
        params.put("code", genre.getCode());
        Integer integer = namedParameterJdbcOperations.queryForObject("select count(*) from genres " +
                        "where name = :name " +
                        "and code = :code",
                params,
                Integer.class);
        return integer > 0;
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            Genre genre = new Genre();
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String code = resultSet.getString("code");
            genre.setId(id);
            genre.setName(name);
            genre.setCode(code);
            return genre;
        }
    }
}
