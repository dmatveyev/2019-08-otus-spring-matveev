package ru.otus.spring01.library.exception;

public class GenreHasBookException extends RuntimeException {
    public GenreHasBookException(String s) {
        super(s);
    }
}
