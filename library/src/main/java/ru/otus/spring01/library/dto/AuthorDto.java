package ru.otus.spring01.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthorDto {

    private String id;
    private String name;
    private List<BookDto> books;
}
