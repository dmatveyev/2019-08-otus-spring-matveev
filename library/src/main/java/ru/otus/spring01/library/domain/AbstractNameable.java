package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractNameable extends AbstractIdentity {

    protected String name;
}
