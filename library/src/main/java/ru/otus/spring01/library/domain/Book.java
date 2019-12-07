package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Books")
public class Book {

    @Id
    private String id;

    private String name;

    private Author author;

    private Genre genre;

    private String isbn;

    public Book() {
    }

    public Book(String id, String name, String isbn) {
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
