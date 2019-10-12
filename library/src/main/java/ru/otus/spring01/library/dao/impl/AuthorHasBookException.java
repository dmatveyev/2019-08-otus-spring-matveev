package ru.otus.spring01.library.dao.impl;

public class AuthorHasBookException extends RuntimeException{
    public AuthorHasBookException(String message) {
        super(message);
    }
}
