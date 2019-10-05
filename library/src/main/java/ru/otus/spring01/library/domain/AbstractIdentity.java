package ru.otus.spring01.library.domain;

import java.util.UUID;

public abstract class AbstractIdentity {

    protected UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
