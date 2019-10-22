package ru.otus.spring01.library.dao.impl;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.PersonDao;
import ru.otus.spring01.library.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class PersonDaoImpl implements PersonDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public PersonDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from persons", new HashMap<>(), Integer.class);
    }

    @Override
    public void insert(Person person) {
        if (person.getId() == null) {
            person.setId(UUID.randomUUID());
        }
        namedParameterJdbcOperations.update("insert into persons (id, `name`, password) " +
                "values (:id, :name, :password)", new BeanPropertySqlParameterSource(person));
    }

    @Override
    public Person getById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select * from persons where id = :id", params, new PersonMapper()
        );
    }

    @Override
    public List<Person> getAll() {
        return namedParameterJdbcOperations.query("select * from persons", new PersonMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from persons where id = :id", params
        );
    }

    @Override
    public Person getByUserAndName(String name, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("password", password);
        List<Person> result = namedParameterJdbcOperations.query(
                "select * from persons where name = :name " +
                        "and password = :password", params, new PersonMapper()
        );
        return result.isEmpty() ? null : result.get(0);
    }

    private static class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            Person person = new Person(id, name);
            person.setPassword(password);
            return person;
        }
    }

}
