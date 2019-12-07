package ru.otus.spring01.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;

public interface GenreDao extends MongoRepository<Genre, String> {

    Genre getById(String id);

    List<Genre> findAll();

    void deleteById(String id);

    Genre getByName(String name);

    boolean existsByNameAndCode(String name, String code);

}
