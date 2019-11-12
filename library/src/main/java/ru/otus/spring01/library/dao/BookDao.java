package ru.otus.spring01.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;
import java.util.UUID;

public interface BookDao extends MongoRepository<Book, UUID> {
    long count();

    Book save(Book book);

    Book getById(UUID id);

    List<Book> findAll();

    Book getByIsbn(String isbn);

    List<Book> getByNameContainingIgnoreCase(String nameLike);

    List<Book> getByGenre(Genre genre);

    List<Book> getByAuthorName(String authorName);

    void deleteById(UUID id);

    boolean existsByNameAndGenreNameAndAuthorName(String bookName, String genreName, String authorName);
}
