package ru.otus.spring01.library.dao;

import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.BookComment;
import ru.otus.spring01.library.domain.Person;

import java.util.List;
import java.util.UUID;

public interface BookCommentDao {

    List<BookComment> getAll();

    List<BookComment> getBookCommentsByPerson(Person person);

    List<BookComment> getBookCommentsByBook(Book book);

    void insertComment(BookComment comment);

    void updateComment(UUID id, String comment);

    void deleteComment(UUID id);
}
