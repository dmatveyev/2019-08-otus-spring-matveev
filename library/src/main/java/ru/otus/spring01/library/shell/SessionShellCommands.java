package ru.otus.spring01.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring01.library.dao.PersonDao;
import ru.otus.spring01.library.domain.Person;
import ru.otus.spring01.library.service.Session;

@ShellComponent
@RequiredArgsConstructor
public class SessionShellCommands {

    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String LOGOUT = "logout";

    private final PersonDao personDao;
    private final Session session;

    @ShellMethod(key = LOGIN, value = "login")
    public String login(String login, String password) {
        Person byUserAndName = personDao.getByUserAndName(login, password);
        if (byUserAndName == null) {
            return "Please insert correct login or password";
        }
        Person newPerson = new Person(login);
        newPerson.setPassword(password);
        personDao.insert(newPerson);
        session.setLogined(true);
        return "Hello " + login;
    }

    @ShellMethod(key = REGISTER, value = "Register new User")
    public String register(@ShellOption String login, @ShellOption String password) {
        Person byUserAndName = personDao.getByUserAndName(login, password);
        if (byUserAndName != null) {
            return "User is ....";
        }
        Person newPerson = new Person(login);
        newPerson.setPassword(password);
        personDao.insert(newPerson);
        return "Registered newUser " + login;
    }

    @ShellMethod(key = LOGOUT, value = "Logout")
    public String logout() {
        session.setLogined(false);
        return "GoodBye!!";
    }

    @ShellMethodAvailability({LOGIN, REGISTER})
    public Availability registerAvailability() {
        return session.isLogined()
                ? Availability.unavailable("You are logined")
                : Availability.available();
    }

    @ShellMethodAvailability({LOGOUT})
    public Availability logoutAvailability() {
        return session.isLogined()
                ? Availability.available()
                : Availability.unavailable("You aren't logined");
    }
}
