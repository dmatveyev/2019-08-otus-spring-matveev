package ru.otus.spring01.dao;

import ru.otus.spring01.dto.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestions();
}
