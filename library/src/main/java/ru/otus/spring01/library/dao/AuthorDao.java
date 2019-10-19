package ru.otus.spring01.library.dao;

import ru.otus.spring01.library.domain.Author;

import java.util.List;
import java.util.UUID;

public interface AuthorDao {

    Long count();

    void insert(Author author);

    Author getById(UUID id);

    List<Author> getAll();

    void deleteById(UUID id);

    boolean contains(Author author);

    Author getByName(String name);
}
