package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "Authors")
public class Author {

    @Id
    private String id;
    private String name;
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "books=" + books +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
