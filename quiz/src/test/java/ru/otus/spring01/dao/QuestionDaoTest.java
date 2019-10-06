package ru.otus.spring01.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.dto.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Questions dao test")
@SpringBootTest
@ActiveProfiles("test")
public class QuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    @DisplayName("Get all Questions")
    public void getAllQuestion() {
        List<Question> questions = questionDao.getQuestions();
        assertNotNull(questions);
    }
}
