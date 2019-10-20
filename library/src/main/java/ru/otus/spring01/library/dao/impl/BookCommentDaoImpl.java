package ru.otus.spring01.library.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring01.library.dao.BookCommentDao;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.BookComment;
import ru.otus.spring01.library.domain.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class BookCommentDaoImpl implements BookCommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookComment> getAll() {
        return entityManager.createQuery("select bc from BookComment bc ", BookComment.class)
                .getResultList();
    }

    @Override
    public List<BookComment> getBookCommentsByPerson(Person person) {
        return entityManager.createQuery("select bc from BookComment bc " +
                "where bc.person.id = :personId", BookComment.class)
                .setParameter("personId", person.getId())
                .getResultList();
    }

    @Override
    public List<BookComment> getBookCommentsByBook(Book book) {
        return entityManager.createQuery("select bc from BookComment bc " +
                "where bc.book.id = :bookId", BookComment.class)
                .setParameter("bookId", book.getId())
                .getResultList();
    }

    @Override
    public void insertComment(BookComment comment) {
        entityManager.persist(comment);
    }

    @Override
    public void updateComment(UUID id, String comment) {
        BookComment bookComment = entityManager.find(BookComment.class, id);
        if (Objects.nonNull(bookComment)) {
            bookComment.setComment(comment);
            entityManager.merge(bookComment);
        }
    }

    @Override
    public void deleteComment(UUID id) {
        entityManager.createQuery("delete from BookComment bc where bc.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
