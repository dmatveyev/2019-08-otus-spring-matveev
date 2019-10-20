package ru.otus.spring01.library.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.PersonDao;
import ru.otus.spring01.library.domain.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        return entityManager.createQuery("select count(p) from Person p", Long.class)
                .getSingleResult();
    }

    @Override
    public void insert(Person person) {
        if (contains(person)) {
            entityManager.persist(person);
        }
    }

    @Override
    public Person getById(UUID id) {
        List<Person> result = entityManager.createQuery("select p from Person p where p.id = :id", Person.class)
                .setParameter("id", id)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Person> getAll() {
        return entityManager.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    @Override
    public void deleteById(UUID id) {
        entityManager.createQuery("delete from Person p where p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Person getByUserAndName(String name, String password) {
        List<Person> result = entityManager.createQuery("select p from Person p" +
                " where p.name = :name " +
                "and p.password = :password ", Person.class)
                .setParameter("name", name)
                .setParameter("password", password)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public boolean contains(Person person) {
        List<Person> resultList = entityManager.createQuery("select p from Person p " +
                "where p.name = :name " +
                "and p.password = :password", Person.class)
                .setParameter("name", person.getName())
                .setParameter("password", person.getPassword())
                .setMaxResults(1)
                .getResultList();
        return resultList.size() > 0;
    }
}
