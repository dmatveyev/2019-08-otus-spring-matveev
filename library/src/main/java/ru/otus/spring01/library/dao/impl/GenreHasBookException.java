package ru.otus.spring01.library.dao.impl;

public class GenreHasBookException extends RuntimeException {
    public GenreHasBookException(String s) {
        super(s);
    }
}
