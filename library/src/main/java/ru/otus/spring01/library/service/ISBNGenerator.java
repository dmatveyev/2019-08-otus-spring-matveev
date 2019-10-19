package ru.otus.spring01.library.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ISBNGenerator {

    private Random random;

    public ISBNGenerator () {
        random = new Random();
    }

    public String generateNumber() {
        return "13-84356-" + Math.abs(random.nextInt());
    }

}
