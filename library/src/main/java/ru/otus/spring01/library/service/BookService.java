package ru.otus.spring01.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.dao.BookCommentDao;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.dao.GenreDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.dto.BookDto;
import ru.otus.spring01.library.exception.AuthorNotFountException;
import ru.otus.spring01.library.exception.GenreNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookCommentDao bookCommentDao;
    private final ISBNGenerator isbnGenerator;


    public void validateAuthor(String authorName, Author author) {
        if (Objects.isNull(author)) {
            throw new AuthorNotFountException(authorName);
        }
    }

    public void validateGenre(String genreName, Genre genre) {
        if (Objects.isNull(genre)) {
            throw new GenreNotFoundException(genreName);
        }
    }

    public String removeBookByIsbn(String isbn) {
        Book book = bookDao.getByIsbn(isbn);
        if (Objects.nonNull(book)) {
            long commentCount = bookCommentDao.countBookCommentByBookId(book.getId());
            if (commentCount > 0) {
                bookCommentDao.deleteById(book.getId());
            }
            bookDao.deleteById(book.getId());
            return "Removed";
        }
        return "Book not found";
    }

    public List<BookDto> getAllBooks() {
        return bookDao.findAll().stream().map(Book::toDto).collect(Collectors.toList());
    }

    public Optional<BookDto> findById(String id) {
        return bookDao.findById(id).map(Book::toDto);
    }

    public BookDto create(BookDto toSave) {

        Book byName = bookDao.getByName(toSave.getName());
        Book book = createBookFromDto(toSave);
        Book saved = bookDao.save(book);
        return saved.toDto();
    }

    private Book createBookFromDto(BookDto toSave) {
        Book book = new Book();
        book.setName(toSave.getName());

        Genre genre = genreDao.getById(toSave.getGenreId());
        book.setGenre(genre);
        Author author = authorDao.getById(toSave.getAuthorId());
        book.setAuthor(author);
        return book;
    }

    public BookDto save(BookDto bookDtoToSave) {
        Book bookFromDto = createBookFromDto(bookDtoToSave);
        return bookDao.save(bookFromDto).toDto();
    }

    public void delete(String id) {
        bookDao.deleteById(id);
    }
}
