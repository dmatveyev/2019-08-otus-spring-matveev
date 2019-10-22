package ru.otus.spring01.library.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Session {

    private boolean isLogined = false;
}
