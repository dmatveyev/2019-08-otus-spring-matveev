package ru.otus.spring01.library.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class PersonDaoJdbc implements PersonDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public PersonDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        // Это просто отсавили, чтобы не переписывать код
        // В идеале всё должно быть на NamedParameterJdbcOperations
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from persons", Integer.class);
    }

    @Override
    public void insert(Person person) {
        if (person.getId() == null) {
            person.setId(UUID.randomUUID());
        }
        jdbc.update("insert into persons (id, `name`) values (?, ?)", person.getId(), person.getName());
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
        return jdbc.query("select * from persons", new PersonMapper());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from persons where id = :id", params
        );
    }

    private static class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        }
    }

}