package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Book {

    private UUID id;
    private String name;

    private Author author;

    private Genre genre;

    private String isbn;

    public Book() {
        this.id = UUID.randomUUID();
    }

    public Book(UUID id, String name, String isbn) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author.getName() +
                ", genre=" + genre.getName() +
                ", isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
