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
import ru.otus.spring01.library.exception.AuthorNotFountException;
import ru.otus.spring01.library.exception.GenreNotFoundException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookCommentDao bookCommentDao;
    private final ISBNGenerator isbnGenerator;

    public void createAndSaveBook(String name, String genreName, String authorName) {
        Genre genre = genreDao.getByName(genreName);
        validateGenre(genreName, genre);
        Author author = authorDao.getByName(authorName);
        validateAuthor(authorName, author);
        Book book = new Book();
        book.setIsbn(isbnGenerator.generateNumber());
        book.setName(name);
        book.setGenre(genre);
        book.setAuthor(author);
        bookDao.save(book);
    }

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
}
