package ru.otus.spring01.library.dao;

import ru.otus.spring01.library.domain.Person;

import java.util.List;
import java.util.UUID;

public interface PersonDao {
    int count();

    void insert(Person person);

    Person getById(UUID id);

    List<Person> getAll();

    void deleteById(int id);
}
