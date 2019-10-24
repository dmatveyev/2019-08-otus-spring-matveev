package ru.otus.spring01.library.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long count() {
        return entityManager.createQuery("select count(b) from Book b", Long.class)
                .getSingleResult();
    }

    @Override
    public void insert(Book book) {
        if (contains(book)) {
            return;
        }
        if (book.getId() == null) {
            book.setId(UUID.randomUUID());
        }
        entityManager.persist(book);
    }

    @Override
    public Book getById(UUID id) {
        List<Book> result = entityManager.createQuery("select b from Book b " +
                        "left join b.author " +
                        "left join b.genre " +
                        "where b.id = :id",
                Book.class)
                .setParameter("id", id)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);

    }

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("select b from Book b " +
                        "left join b.author " +
                        "left join b.genre",
                Book.class)
                .getResultList();
    }

    @Override
    public Book getByISBN(String isbn) {
        List<Book> books = entityManager.createQuery(
                "select b from Book b " +
                        "left join b.author " +
                        "left join b.genre " +
                        "where isbn = :isbn", Book.class
        ).setParameter("isbn", isbn)
                .getResultList();
        return books.isEmpty() ? null : books.get(0);
    }

    @Override
    public List<Book> getByNameLike(String nameLike) {
        nameLike = ("%" + nameLike + "%").toLowerCase();
        return entityManager.createQuery("select b from Book b " +                         "left join b.author " +
                "left join b.genre " +
                "where LOWER(b.name) like :nameLike", Book.class)
                .setParameter("nameLike", nameLike)
                .getResultList();
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        return entityManager.createQuery(
                "select b from Book b " +
                        "left join b.author " +
                        "left join b.genre " +
                        "where b.genre.name = :genre", Book.class)
                .setParameter("genre", genre.getName())
                .getResultList();
    }

    @Override
    public List<Book> getByAuthorName(String authorName) {
        return entityManager.createQuery("select b from Book b " +
                "left join b.author " +
                "left join b.genre " +
                "where b.author.name = :authorName", Book.class)
                .setParameter("authorName", authorName)
                .getResultList();
    }

    @Override
    public void deleteById(UUID id) {
        entityManager.createQuery("delete from Book b " +
                "where b.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public boolean contains(Book book) {
        List<Book> result = entityManager.createQuery(
                "select b from Book b " +
                        " join fetch b.author " +
                        " join fetch b.genre " +
                        " where b.name = :bookName and b.author.name = :authorName " +
                        "and b.genre.id = :genreId " +
                        "and b.isbn = :isbn ", Book.class)
                .setParameter("bookName", book.getName())
                .setParameter("authorName", book.getAuthor().getName())
                .setParameter("genreId", book.getGenre().getId())
                .setParameter("isbn", book.getIsbn())
                .setMaxResults(1)
                .getResultList();
        return !result.isEmpty();
    }
}
