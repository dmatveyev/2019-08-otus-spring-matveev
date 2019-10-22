package ru.otus.spring01.library.exception;

public class AuthorHasBookException extends RuntimeException{
    public AuthorHasBookException(String message) {
        super(message);
    }
}
