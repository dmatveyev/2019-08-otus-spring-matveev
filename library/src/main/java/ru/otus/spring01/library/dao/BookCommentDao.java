package ru.otus.spring01.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.BookComment;
import ru.otus.spring01.library.domain.Person;

import java.util.List;

public interface BookCommentDao extends MongoRepository<BookComment, String> {

    List<BookComment> findAll();

    List<BookComment> getBookCommentsByPerson(Person person);

    List<BookComment> getBookCommentsByBook(Book book);

    long countBookCommentByBookId(String bookId);

    BookComment getById(String id);

    void deleteByBookId(String bookId);
}
