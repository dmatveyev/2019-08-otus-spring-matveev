package ru.otus.spring01.library.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring01.library.domain.Person;

import java.util.List;
import java.util.UUID;

public interface PersonDao extends CrudRepository<Person, UUID> {
    long count();

    Person save(Person person);

    Person getById(UUID id);

    List<Person> findAll();

    Person getByNameAndPassword(String name, String password);
}
