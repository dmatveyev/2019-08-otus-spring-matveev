package ru.otus.spring01.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private AuthorDto author;
    private GenreDto genre;
    private String isbn;
}
