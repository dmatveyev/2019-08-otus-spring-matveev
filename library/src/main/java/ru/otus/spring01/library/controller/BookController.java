package ru.otus.spring01.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring01.library.dto.BookDto;
import ru.otus.spring01.library.service.AuthorService;
import ru.otus.spring01.library.service.BookService;
import ru.otus.spring01.library.service.GenreService;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @GetMapping("/all")
    public String getAll(Model model) {
        List<BookDto> allBooks = bookService.getAllBooks();
        model.addAttribute("books", allBooks);
        return "books";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        BookDto bookDto = bookService.findById(id).orElseThrow(NullPointerException::new);
        model.addAttribute("book", bookDto);
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "editBook";
    }

    @PostMapping("/edit")
    public String save(@RequestParam("id") String id, BookDto bookDtoToSave, Model model) {
        BookDto save = bookService.save(bookDtoToSave);
        model.addAttribute("book", save);
        return "redirect:/books/all";
    }

    @PostMapping("/create")
    public String create(BookDto bookDto, Model model) {
        BookDto bookDtoDto = bookService.create(bookDto);
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "redirect:/books/all";
    }

    @GetMapping("/create")
    public String showCreateForm (Model model) {
        model.addAttribute("book", new BookDto());
        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "addBook";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") String id, Model model) {
        bookService.delete(id);
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "redirect:/books/all";
    }
}
