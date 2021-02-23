package ru.otus.spring01.library.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.otus.spring01.library.dao.GenreDao;
import ru.otus.spring01.library.domain.Genre;
import ru.otus.spring01.library.dto.GenreDto;
import ru.otus.spring01.library.exception.EntityExistsException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDao genreDao;

    public List<GenreDto> getAllGenres(int page, int count) {

        return genreDao.findAll(PageRequest.of(page-1, count)).stream().map(Genre::toDto).collect(Collectors.toList());
    }

    public GenreDto create(GenreDto genreDto) {
        Genre byName = genreDao.getByName(genreDto.getName());
        if (byName != null) {
            throw new EntityExistsException(String.format("Genre '%s' exists!!", genreDto.getName()));
        }
        Genre genre = createGenreFromDto(genreDto);
        Genre saved = genreDao.save(genre);
        return saved.toDto();
    }

    public GenreDto save(GenreDto genreDto) {
        val genre = createGenreFromDto(genreDto);
        val save = genreDao.save(genre);
        return save.toDto();
    }

    public void delete(String id) {
        genreDao.deleteById(id);
    }

    public Optional<GenreDto> findById(String id) {
        Optional<Genre> byId = genreDao.findById(id);
        return byId.map(Genre::toDto);
    }

    private Genre createGenreFromDto(GenreDto genreDto) {
        Genre genre = new Genre();
        genre.setId(genreDto.getId());
        genre.setName(genreDto.getName());
        genre.setCode(genreDto.getCode());
        return genre;
    }

    public Long getGenresCount() {
        return genreDao.count();
    }
}
