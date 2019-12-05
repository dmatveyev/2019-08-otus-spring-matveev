package ru.otus.spring01.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.service.BookService;
import ru.otus.spring01.library.service.Session;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookShellCommands {

    public static final String ADD_BOOK = "addBook";
    public static final String BOOK_BY_NAME = "bookByName";
    public static final String REMOVE_BOOK = "removeBook";
    private final Session session;
    private final BookService bookService;
    private final BookDao bookDao;



    @ShellMethod(key = ADD_BOOK, value = "Adding book to library")
    public String addBook(String name, String genreName, String authorName) {

        bookService.createAndSaveBook(name, genreName, authorName);
        return "Book was added";   }



    @ShellMethod(key = BOOK_BY_NAME, value = "Gets book list by name")
    public List<Book> getBookByName(String bookName) {
        return bookDao.getByNameContainingIgnoreCase(bookName);
    }

    @ShellMethod(key = REMOVE_BOOK, value = "Removes book by isbn")
    public String removeBook(String isbn) {
        return bookService.removeBookByIsbn(isbn);
    }

    @ShellMethodAvailability
    public Availability logoutAvailability() {
        return session.isLogined()
                ? Availability.available()
                : Availability.unavailable("You aren't logined");
    }
}
