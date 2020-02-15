package ru.otus.spring01.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private String authorId;
    private String author;
    private String genreId;
    private String genre;
    private String isbn;
}
