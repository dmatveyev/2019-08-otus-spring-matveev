package ru.otus.spring01.library.exception;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String s) {
        super(s);
    }
}
