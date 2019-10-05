package ru.otus.spring01.library.domain;

public abstract class AbstractNameable extends AbstractIdentity {

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
