package ru.otus.spring01.library.dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Person;

import java.util.List;
import java.util.UUID;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        // Это просто отсавили, чтобы не переписывать код
        // В идеале всё должно быть на NamedParameterJdbcOperations
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void insert(Author author) {
        if (author.getId() == null) {
            author.setId(UUID.randomUUID());
        }
        namedParameterJdbcOperations.update(
                "insert into authors (id, `name`) values (:id, :name)", new BeanPropertySqlParameterSource(author));
    }

    @Override
    public Person getById(UUID id) {
        return null;
    }

    @Override
    public List<Person> getAll() {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
