package ru.otus.spring01.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.domain.Book;
import ru.otus.spring01.library.service.Session;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShellCommands {

    public static final String COUNT_AUTHORS = "countAuthors";
    public static final String CREATE_AUTHOR = "createAuthor";
    public static final String DELETE_AUTHOR = "deleteAuthor";
    private final Session session;
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @ShellMethod(key = COUNT_AUTHORS, value = "get Authors count")
    public int getAuthorCount() {
        return authorDao.count();
    }

    @ShellMethod(key = CREATE_AUTHOR, value = "Creating new Author")
    public String createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        if (authorDao.contains(author)) {
            return "Author is contained";
        }
        authorDao.insert(author);
        return "Created";
    }

    @ShellMethod(key = DELETE_AUTHOR, value = "Deleting author")
    public String deleteAuthor(String name) {
        Author byName = authorDao.getByName(name);
        if (byName == null) {
            return "Author isn't contained";
        }
        List<Book> byAuthorName = bookDao.getByAuthorName(name);
        if (!byAuthorName.isEmpty()) {
            return "There are books by author";
        }
        authorDao.deleteById(byName.getId());
        return "Deleted";
    }

    @ShellMethodAvailability
    public Availability logoutAvailability() {
        return session.isLogined()
                ? Availability.available()
                : Availability.unavailable("You aren't logined");
    }
}
