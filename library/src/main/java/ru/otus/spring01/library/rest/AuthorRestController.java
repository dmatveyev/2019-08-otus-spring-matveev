package ru.otus.spring01.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.dto.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthorRestController {

    private final AuthorDao authorDao;

    @GetMapping("/authors")
    public List<AuthorDto> getAuthors() {
        return authorDao.findAll().stream().map(Author::toDto).collect(Collectors.toList());
    }

    @PostMapping(value = "/author/")
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto authorDto) {

        Author author = new Author();
        author.setName(authorDto.getName());
        Author saved = authorDao.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.toDto());
    }
}
