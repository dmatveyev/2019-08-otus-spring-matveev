package ru.otus.spring01.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring01.library.dao.AuthorDao;
import ru.otus.spring01.library.domain.Author;
import ru.otus.spring01.library.dto.AuthorDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorDao authorDao;

    public List<AuthorDto> getAllAuthors() {
        return authorDao.findAll().stream().map(Author::toDto).collect(Collectors.toList());
    }

    public Optional<AuthorDto> findById(String id) {
        return authorDao.findById(id).map(Author::toDto);
    }

    public AuthorDto save(AuthorDto toSave) {
        return authorDao.save(fromDto(toSave)).toDto();
    }

    public AuthorDto create(AuthorDto toSave) {
        return authorDao.save(fromDto(toSave)).toDto();

    }

    public void delete(String id) {
        authorDao.deleteById(id);
    }

    private Author fromDto(AuthorDto dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        return author;
    }
}
