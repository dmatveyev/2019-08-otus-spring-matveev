package ru.otus.spring01.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring01.library.dto.BookDto;
import ru.otus.spring01.library.exception.AuthorNotFountException;
import ru.otus.spring01.library.exception.GenreNotFoundException;
import ru.otus.spring01.library.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDto> getBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping(value = "/book/")
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {

        BookDto andSaveBook = bookService.createAndSaveBook(bookDto.getName(), bookDto.getGenre().getName(), bookDto.getAuthor().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(andSaveBook);
    }

    @ExceptionHandler(value = AuthorNotFountException.class)
    public ResponseEntity<?> authorNotFountExceptionHandler(AuthorNotFountException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = GenreNotFoundException.class)
    public ResponseEntity<?> genreNotFountExceptionHandler(GenreNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
