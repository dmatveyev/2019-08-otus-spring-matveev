package ru.otus.spring01.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dto.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class QuestionServiceImplTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    public void getNextQuestion() {
        List<Question> questions = questionDao.getQuestions();
        assertNotNull(questions);
        assertEquals(5, questions.size());
    }
}
