package ru.otus.spring01.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.dto.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Questions services test")
@SpringBootTest
@ActiveProfiles("test")
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("Get question by number")
    public void getQuestionByNumber() {
        int countQuestion = questionService.getCountQuestion();
        int questionNumber = (int) (Math.random() * ++countQuestion - 1) + 1;
        Question questionByNumber = questionService.getQuestionByNumber(questionNumber);
        assertEquals(questionNumber, questionByNumber.getQuestionNumber().intValue());
    }

    @Test
    @DisplayName("Get next question")
    public void getNextQuestion() {
        Question nextQuestion = questionService.getNextQuestion();
        assertNotNull(nextQuestion);
        String correctAnswer = nextQuestion.getCorrectAnswer();
        assertNotNull(correctAnswer);
        Integer questionNumber = nextQuestion.getQuestionNumber();
        assertEquals(1, questionNumber.intValue());
    }

}