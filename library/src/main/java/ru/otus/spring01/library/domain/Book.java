package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
public class Book extends AbstractNameable {

    public Book() {
        this.id = UUID.randomUUID();
    }

    private Author author;

    private Genre genre;

    private String isbn;

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author.getName() +
                ", genre=" + genre +
                ", isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
