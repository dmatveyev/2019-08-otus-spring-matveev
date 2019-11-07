package ru.otus.spring01.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
public class Genre {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    public Genre() {
        this.id = UUID.randomUUID();
    }

}
