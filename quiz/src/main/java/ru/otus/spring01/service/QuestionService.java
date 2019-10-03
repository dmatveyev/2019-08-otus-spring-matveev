package ru.otus.spring01.service;

import ru.otus.spring01.dto.Question;

public interface QuestionService {

    String getScore();

    Question getNextQuestion();
}
