package ru.otus.spring01.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
public class Genre {

    private UUID id;

    private String name;

    @Column(name = "CODE")
    private String code;

    public Genre() {
        this.id = UUID.randomUUID();
    }

}
