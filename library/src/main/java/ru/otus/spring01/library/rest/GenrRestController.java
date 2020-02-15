package ru.otus.spring01.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring01.library.dto.GenreDto;
import ru.otus.spring01.library.exception.EntityExistsException;
import ru.otus.spring01.library.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GenrRestController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public List<GenreDto> getGenres() {
        return genreService.getAllGenres();
    }

    @PostMapping(value = "/genre/")
    public ResponseEntity<GenreDto> create(@RequestBody GenreDto genreDto) {
        GenreDto saved = genreService.create(genreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> entityExistsException(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
