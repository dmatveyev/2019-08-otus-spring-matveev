package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Author extends AbstractNameable {

    public Author() {
        this.id = UUID.randomUUID();
    }

    public Author(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    List<Book> books;

    @Override
    public String toString() {
        return "Author{" +
                "books=" + books +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
