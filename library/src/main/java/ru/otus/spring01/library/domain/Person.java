package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Person {

    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
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
