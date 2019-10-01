package ru.otus.spring01.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dto.Question;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class QuestionServiceImplTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    public void getNextQuestion() {
        List<Question> questions = questionDao.getQuestions();
        assertNotNull(questions);
    }
}
