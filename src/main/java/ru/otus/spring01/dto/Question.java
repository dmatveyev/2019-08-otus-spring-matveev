package ru.otus.spring01.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Question {

    private String question;

    private List<String> answers;

    public Question(String question, String ... answers) {
        this.question = question;
        this.answers = new ArrayList<>(Arrays.asList(answers));
    }
}
