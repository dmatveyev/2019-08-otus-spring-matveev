package ru.otus.spring01.library.dao;

import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.domain.Person;

import java.util.List;
import java.util.UUID;

public interface GenreDao {
    int count();

    void insert(Genre genre);

    Genre getById(UUID id);

    List<Genre> getAll();

    void deleteById(UUID id);

    Genre getByName(String name);
}
