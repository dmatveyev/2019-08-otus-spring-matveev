package ru.otus.spring01.library.domain;

import java.util.UUID;

public class Person extends AbstractNameable{

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
