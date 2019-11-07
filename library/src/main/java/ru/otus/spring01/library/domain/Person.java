package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document(collection = "persons")
public class Person {

    @Id
    private UUID id;
    private String name;
    private String password;

    public Person() {}

    public Person(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
