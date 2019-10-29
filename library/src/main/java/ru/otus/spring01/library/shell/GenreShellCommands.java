package ru.otus.spring01.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring01.library.dao.BookDao;
import ru.otus.spring01.library.dao.GenreDao;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.service.Session;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class GenreShellCommands {

    public static final String COUNT_GENRES = "countGenres";
    public static final String CREATE_GENRE = "createGenre";
    public static final String GENRES = "genres";
    public static final String DELETE_GENRE = "deleteGenre";
    private final Session session;
    private final GenreDao genreDao;
    private final BookDao bookDao;

    @ShellMethod(key = COUNT_GENRES, value = "Get genres count")
    public int getGenreCount() {
        return genreDao.findAll().size();
    }

    @ShellMethod(key = CREATE_GENRE, value = "Creating new Genre")
    public String createGenre(@ShellOption String name, @ShellOption String code) {
        Genre genre = new Genre();
        genre.setName(name);
        genre.setCode(code);
        if (genreDao.existsById(genre.getId())) {
            return "Genre is contained";
        }
        genreDao.save(genre);
        return "Created";
    }

    @ShellMethod(key = GENRES, value = "List genres")
    public List<Genre> genres() {
        return genreDao.findAll();
    }

    @ShellMethod(key = DELETE_GENRE, value = "Delete genre by name")
    public String delete(String name) {
        Genre byName = genreDao.getByName(name);
        if (byName == null) {
            return "Genre isn't contained";
        }
        if (!bookDao.getByGenre(byName).isEmpty()) {
            return "There are books by genre " + byName.getName();
        }
        genreDao.deleteById(byName.getId());
        return "Deleted";
    }


    @ShellMethodAvailability
    public Availability logoutAvailability() {
        return session.isLogined()
                ? Availability.available()
                : Availability.unavailable("You aren't logined");
    }
}
