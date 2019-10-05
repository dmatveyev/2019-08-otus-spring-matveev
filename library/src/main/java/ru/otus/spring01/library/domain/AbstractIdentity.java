package ru.otus.spring01.library.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class AbstractIdentity {

    protected UUID id;
}
