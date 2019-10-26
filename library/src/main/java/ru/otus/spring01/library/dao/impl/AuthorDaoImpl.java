package ru.otus.spring01.library.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.exception.AuthorHasBookException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        Query query = entityManager.createQuery(
                "select count(a) from Author a");
        return (Long) query.getSingleResult();
    }

    @Override
    public void insert(Author author) {
        if (contains(author)) {
            return;
        }
        if (author.getId() == null) {
            author.setId(UUID.randomUUID());
        }
        entityManager.persist(author);
    }

    @Override
    public Author getById(UUID id) {
        List<Author> result = entityManager.createQuery("select a from Author a " +
                "where a.id = :id", Author.class)
                .setParameter("id", id)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Author> getAll() {
        List<Author> resultList = entityManager.createQuery("select distinct a from Author a " +
                " left join fetch a.books", Author.class)
                .getResultList();
        return resultList;
    }

    @Override
    public void deleteById(UUID authorId) {
        Long count = (Long) entityManager.createQuery("select count(b) from Book b where b.author.id = :authorId")
                .setParameter("authorId", authorId)
                .getSingleResult();
        if (count > 0) {
            throw new AuthorHasBookException("Author has books, which is user");
        }
        entityManager.createQuery("delete from Author a where a.id = :authorId")
                .setParameter("authorId", authorId)
                .executeUpdate();
    }

    @Override
    public boolean contains(Author author) {
        Long count = (Long) entityManager.createQuery("select count(a) from Author a where a.name = :name")
                .setParameter("name", author.getName())
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Author getByName(String name) {
        List<Author> result = entityManager.createQuery("select a from Author a where a.name = :name",
                Author.class)
                .setParameter("name", name)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
}
