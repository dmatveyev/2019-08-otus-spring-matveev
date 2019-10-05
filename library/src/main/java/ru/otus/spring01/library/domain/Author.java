package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Author extends AbstractNameable {

    List<Book> books;
}
