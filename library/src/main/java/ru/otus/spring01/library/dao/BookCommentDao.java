package ru.otus.spring01.library.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.BookComment;
import ru.otus.spring01.library.domain.Person;

import java.util.List;
import java.util.UUID;

public interface BookCommentDao extends CrudRepository<BookComment, UUID> {

    List<BookComment> findAll();

    List<BookComment> getBookCommentsByPerson(Person person);

    List<BookComment> getBookCommentsByBook(Book book);

    BookComment save(BookComment comment);

    void deleteCommentById(UUID id);
}
