package ru.otus.spring01.library.dao;

import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;
import java.util.UUID;

public interface BookDao {
    Long count();

    void insert(Book book);

    Book getById(UUID id);

    List<Book> getAll();

    Book getByISBN(String isbn);

    List<Book> getByNameLike(String nameLike);

    List<Book> getByGenre(Genre genre);

    List<Book> getByAuthorName(String authorName);

    void deleteById(UUID id);

    boolean contains(Book book);
}
