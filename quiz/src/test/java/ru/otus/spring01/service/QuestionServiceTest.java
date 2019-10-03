package ru.otus.spring01.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dto.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Questions services test")
@SpringBootTest
@ActiveProfiles("test")
class QuestionServiceTest {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("Get all Questions")
    public void getAllQuestion() {
        List<Question> questions = questionDao.getQuestions();
        assertNotNull(questions);
        assertEquals(questionService.getCountQuestion(), questions.size());
    }

    @Test
    @DisplayName("Get question by number")
    public void getQuestionByNumber() {
        int countQuestion = questionService.getCountQuestion();
        int v = (int) (Math.random() * ++countQuestion - 1) + 1;
        System.out.println(v);
        Question questionByNumber = questionService.getQuestionByNumber(v);
        assertEquals(v, questionByNumber.getQuestionNumber().intValue());

    }

}