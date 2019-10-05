package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book extends AbstractNameable {

    private Author author;

    private Genre genre;

    private String ISBN;
}
