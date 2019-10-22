package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Person {

    private UUID id;
    private String name;
    private String password;

    public Person(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
