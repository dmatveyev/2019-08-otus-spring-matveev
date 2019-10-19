package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Person {

    private UUID id;
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
