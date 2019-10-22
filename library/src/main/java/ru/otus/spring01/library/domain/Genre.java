package ru.otus.spring01.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Genre {

    private UUID id;

    private String name;

    private String code;

    public Genre() {
        this.id = UUID.randomUUID();
    }

}
