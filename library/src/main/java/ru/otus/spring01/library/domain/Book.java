package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class Book extends AbstractNameable {

    public Book() {
        this.id = UUID.randomUUID();
    }

    private Author author;

    private Genre genre;

    private String isbn;



}
