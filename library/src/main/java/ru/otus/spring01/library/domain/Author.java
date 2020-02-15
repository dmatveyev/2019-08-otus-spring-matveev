package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring01.library.dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public AuthorDto toDto() {
        AuthorDto authorDto = new AuthorDto(id, name, books.stream().map(Book::toDto).collect(Collectors.toList()));
        return authorDto;
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
