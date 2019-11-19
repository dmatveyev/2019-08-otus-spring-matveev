package ru.otus.spring01.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.exception.AuthorNotFountException;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.dao.GenreDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.exception.GenreNotFoundException;
import ru.otus.spring01.library.service.ISBNGenerator;
import ru.otus.spring01.library.service.Session;

import java.util.List;
import java.util.Objects;

@ShellComponent
@RequiredArgsConstructor
public class BookShellCommands {

    public static final String ADD_BOOK = "addBook";
    public static final String BOOK_BY_NAME = "bookByName";
    public static final String REMOVE_BOOK = "removeBook";
    private final Session session;
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final ISBNGenerator isbnGenerator;


    @ShellMethod(key = ADD_BOOK, value = "Adding book to library")
    public String addBook(String name, String genreName, String authorName) {
        Genre genre = genreDao.getByName(genreName);
        if (Objects.isNull(genre)) {
            throw new GenreNotFoundException(genreName);
        }
        Author author = authorDao.getByName(authorName);
        if (Objects.isNull(author)) {
            throw new AuthorNotFountException(authorName);
        }
        Book book = new Book();
        book.setIsbn(isbnGenerator.generateNumber());
        book.setName(name);
        book.setGenre(genre);
        book.setAuthor(author);
        bookDao.save(book);
        return "Book was added";
    }

    @ShellMethod(key = BOOK_BY_NAME, value = "Gets book list by name")
    public List<Book> getBookByName(String bookName) {
        return bookDao.getByNameContainingIgnoreCase(bookName);
    }

    @ShellMethod(key = REMOVE_BOOK, value = "Removes book by isbn")
    public String removeBook(String isbn) {
        Book book = bookDao.getByIsbn(isbn);
        if (Objects.nonNull(book)) {
            bookDao.deleteById(book.getId());
            return "Removed";
        }
        return "Book not found";
    }

    @ShellMethodAvailability
    public Availability logoutAvailability() {
        return session.isLogined()
                ? Availability.available()
                : Availability.unavailable("You aren't logined");
    }
}
