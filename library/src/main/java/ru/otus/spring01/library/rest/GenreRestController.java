package ru.otus.spring01.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring01.library.dto.GenreDto;
import ru.otus.spring01.library.exception.EntityExistsException;
import ru.otus.spring01.library.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public List<GenreDto> getGenres(@RequestParam("page") int page, @RequestParam("count") int count) {
        return genreService.getAllGenres(page, count);
    }

    @GetMapping("/genres/count")
    public Long getGenresCount() {
        return genreService.getGenresCount();
    }

    @PostMapping(value = "/genre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreDto> create(@RequestBody GenreDto genreDto) {
        GenreDto saved = genreService.create(genreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping(value = "/genre/{id}")
    public ResponseEntity<GenreDto> delete(@PathVariable("id") String genreId) {
        genreService.delete(genreId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> entityExistsException(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
