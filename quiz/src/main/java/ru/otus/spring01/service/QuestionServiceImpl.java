package ru.otus.spring01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dto.Question;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final Queue<Question> questionQueue;
    private final List<Question> questions;
    private int countQuestion;

    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionQueue = new ArrayDeque<>();
        List<Question> questionsList = questionDao.getQuestions();
        this.questions = new ArrayList<>(questionsList);
        questionQueue.addAll(questions);
        countQuestion = questions.size();
    }

    @Override
    public String getScore() {
        long countCorrectAnswers = questions
                .stream()
                .filter(question -> question.getCorrectAnswer().equalsIgnoreCase(question.getUserAnswer()))
                .count();
        return (countCorrectAnswers + "/" + questions.size());
    }

    @Override
    public Question getNextQuestion() {
        return questionQueue.poll();
    }

    @Override
    public Question getQuestionByNumber(int number) {
        if (number > 0 && number <= countQuestion ) {
            return questions.get(number - 1);
        } else{
            return null;
        }
    }

    @Override
    public int getCountQuestion() {
        return countQuestion;
    }
}
