package ru.otus.spring01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dto.Question;

import java.util.List;

@Component
public class QuestionServiceImpl implements QuestionService {

    private final IOService ioService;
    private final List<Question> questions;

    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao, IOService ioService) {
        this.questions = questionDao.getQuestions();
        this.ioService = ioService;
    }

    @Override
    // TODO: 09.09.2019 Возможно стоит возвращать количество правильных ответов
    public void askQuestions() {
        for (Question question : questions) {
            List<String> answers = question.getAnswers();
            ioService.printString(question.getQuestion());
            String resultAnswer;
            if (answers.size() > 0) {
                for (int i = 0; i < answers.size(); i++) {
                    ioService.printString(i + 1 + ") " + answers.get(i));
                }
                int userAnswer = ioService.readInt();
                while ((0 >= userAnswer) || userAnswer > (answers.size())) {
                    ioService.printString("Please choose correct answer!");
                    userAnswer = ioService.readInt();
                }
                resultAnswer = String.valueOf(userAnswer);
            } else {
                resultAnswer = ioService.readString();
            }
            question.setUserAnswer(resultAnswer);
        }
    }

    @Override
    public String getScore() {
        long countCorrectAnswers = questions
                .stream()
                .filter(question -> question.getUserAnswer().equalsIgnoreCase(question.getCorrectAnswer()))
                .count();
        return (countCorrectAnswers + "/" + questions.size());
    }
}
