package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Author {

    private UUID id;
    private String name;

    public Author() {
        this.id = UUID.randomUUID();
    }

    public Author(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany
    private List<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        return "Author{" +
                "books=" + books +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
