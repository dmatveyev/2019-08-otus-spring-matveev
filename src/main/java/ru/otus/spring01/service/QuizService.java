package ru.otus.spring01.service;

import org.springframework.shell.standard.ShellMethod;

public interface QuizService {

    @ShellMethod("Start testing")
    void start();

    @ShellMethod(key = "next", value = "get next question")
    String getNextQuestion();

    @ShellMethod(key = "answer:", value = "Read user answer")
    void readUserAnswer(String answer);
}
