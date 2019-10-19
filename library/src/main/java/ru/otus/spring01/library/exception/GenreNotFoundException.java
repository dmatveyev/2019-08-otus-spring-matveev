package ru.otus.spring01.library.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String genreName) {
        super(String.format("Genre \'%s\' not found", genreName));
    }
}
