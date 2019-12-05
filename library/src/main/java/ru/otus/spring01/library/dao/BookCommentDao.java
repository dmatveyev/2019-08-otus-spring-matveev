package ru.otus.spring01.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.BookComment;
import ru.otus.spring01.library.domain.Person;

import java.util.List;
import java.util.UUID;

public interface BookCommentDao extends MongoRepository<BookComment, UUID> {

    List<BookComment> findAll();

    List<BookComment> getBookCommentsByPerson(Person person);

    List<BookComment> getBookCommentsByBook(Book book);

    long countBookCommentByBookId(UUID bookId);

    BookComment save(BookComment comment);

    BookComment getById(UUID id);

    void deleteByBookId(UUID bookId);
}
