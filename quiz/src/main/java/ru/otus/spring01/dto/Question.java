package ru.otus.spring01.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Question {

    private Integer questionNumber;

    private String question;

    private List<String> answers;

    private String userAnswer;

    private String correctAnswer;

    public Question(Integer questionNumber, String question, String correctAnswer, String... answers) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = new ArrayList<>(Arrays.asList(answers));
    }
}
