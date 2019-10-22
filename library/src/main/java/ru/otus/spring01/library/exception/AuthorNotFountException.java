package ru.otus.spring01.library.exception;

public class AuthorNotFountException extends RuntimeException{
    public AuthorNotFountException(String authorName) {
        super(String.format("Author \'%s\' not found", authorName));
    }
}
