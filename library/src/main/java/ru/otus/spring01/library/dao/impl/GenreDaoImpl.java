package ru.otus.spring01.library.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.GenreDao;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.exception.GenreHasBookException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        return entityManager.createQuery("select count(g) from Genre g", Long.class)
                .getSingleResult();
    }

    @Override
    public void insert(Genre genre) {
        if (!contains(genre)) {
            entityManager.persist(genre);
        }
    }

    @Override
    public Genre getById(UUID id) {
        List<Genre> result = entityManager.createQuery("select g from Genre g " +
                "where g.id = :id", Genre.class)
                .setParameter("id", id)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Genre> getAll() {
        return entityManager.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("genreId", id);
        List<Book> booksByGenre = entityManager.createQuery("select b from Book b " +
                        "where b.genre.id = :genreId",
                Book.class)
                .setParameter("genreId", id)
                .getResultList();
        if (booksByGenre.size() > 0) {
            throw new GenreHasBookException("There are any books by this genre");
        }
        entityManager.createQuery("delete from Genre where id = :genreId")
                .setParameter("genreId", id)
                .executeUpdate();
    }

    @Override
    public Genre getByName(String name) {
        Map<String, String> param = Collections.singletonMap("name", name);

        List<Genre> result = entityManager.createQuery("select g from Genre g" +
                " where g.name = :name ", Genre.class)
                .setParameter("name", name)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public boolean contains(Genre genre) {
        List<Genre> resultList = entityManager.createQuery("select g from Genre g " +
                "where g.name = :name " +
                "and g.code = :code", Genre.class)
                .setParameter("name", genre.getName())
                .setParameter("code", genre.getCode())
                .setMaxResults(1)
                .getResultList();
        return resultList.size() > 0;
    }
}
