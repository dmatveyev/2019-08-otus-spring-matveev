package ru.otus.spring01.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring01.library.domain.Author;

import java.util.List;

public interface AuthorDao extends MongoRepository<Author, String> {

    Author getById(String id);

    List<Author> findAll();

    void deleteById(String id);

    boolean existsByName(String name);

    Author getByName(String name);
}
