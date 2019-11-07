package ru.otus.spring01.library.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;
import java.util.UUID;

public interface GenreDao extends CrudRepository<Genre, UUID> {
    Genre save(Genre genre);

    Genre getById(UUID id);

    List<Genre> findAll();

    void deleteById(UUID id);

    Genre getByName(String name);

    boolean existsByNameAndCode(String name, String code);

}
