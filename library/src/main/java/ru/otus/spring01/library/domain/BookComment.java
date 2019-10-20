package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
public class BookComment {

    @Id
    @Column(name = "ID")
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Person person;

    @ManyToOne
    private Book book;

    @Column(name = "COMMENT")
    @Lob
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookComment that = (BookComment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
