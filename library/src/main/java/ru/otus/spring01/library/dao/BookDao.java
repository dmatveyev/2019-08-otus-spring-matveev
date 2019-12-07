package ru.otus.spring01.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;

public interface BookDao extends MongoRepository<Book, String> {
    long count();

    Book getById(String id);

    List<Book> findAll();

    Book getByIsbn(String isbn);

    List<Book> getByNameContainingIgnoreCase(String nameLike);

    List<Book> getByGenre(Genre genre);

    List<Book> getByAuthorName(String authorName);

    void deleteById(String id);

    boolean existsByNameAndGenreNameAndAuthorName(String bookName, String genreName, String authorName);
}
