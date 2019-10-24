package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Book {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENRE_ID")
    private Genre genre;

    @Column(name = "ISBN")
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
