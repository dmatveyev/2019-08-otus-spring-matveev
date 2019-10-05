package ru.otus.spring01.library.dao;

import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Person;

import java.util.List;
import java.util.UUID;

public interface AuthorDao {

    int count();

    void insert(Author author);

    Person getById(UUID id);

    List<Person> getAll();

    void deleteById(UUID id);
}
