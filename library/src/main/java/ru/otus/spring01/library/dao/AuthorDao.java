package ru.otus.spring01.library.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring01.library.domain.Author;

import java.util.List;
import java.util.UUID;

public interface AuthorDao extends CrudRepository<Author, UUID> {

    Author save(Author author);

    Author getById(UUID id);

    List<Author> findAll();

    void deleteById(UUID id);

    boolean existsByName(String name);

    Author getByName(String name);
}
