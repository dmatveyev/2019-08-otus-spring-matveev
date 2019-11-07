package ru.otus.spring01.library.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.repository.EntityGraph.*;

public interface BookDao extends CrudRepository<Book, UUID> {
    long count();

    Book save(Book book);

    Book getById(UUID id);

    @EntityGraph(value = "book.genres.authors", type = EntityGraphType.LOAD)
    List<Book> findAll();

    Book getByIsbn(String isbn);

    List<Book> getByNameContainingIgnoreCase(String nameLike);

    List<Book> getByGenre(Genre genre);

    List<Book> getByAuthorName(String authorName);

    void deleteById(UUID id);

    boolean existsByNameAndGenreNameAndAuthorName(String bookName, String genreName, String authorName);
}
