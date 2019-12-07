package ru.otus.spring01.library.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring01.library.domain.Person;

import java.util.List;

public interface PersonDao extends CrudRepository<Person, String> {

    long count();

    Person getById(String id);

    List<Person> findAll();

    Person getByNameAndPassword(String name, String password);
}
