package ru.otus.spring01.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.dto.Question;
import ru.otus.spring01.localization.LocalizationService;
import ru.otus.spring01.localization.MessageConstants;
import ru.otus.spring01.service.QuestionService;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.spring01.shell.QuizShellCommands.DEFAULT_NAME;

@DisplayName("Shell Commands test")
@SpringBootTest
@ActiveProfiles("test")
class QuizShellCommandsTest {

    @Autowired
    private Shell shell;

    @Autowired
    private LocalizationService localizationService;

    @Autowired
    private QuestionService questionService;

    public static final String LOGIN_WITH_CUSTOM_DATA = QuizShellCommands.START_TESTING_KEY + " %s %s";

    @DisplayName("Успешный логин под дефолтным пользователем")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void successDefaultUserLogin() {
        String result = (String) shell.evaluate(() -> QuizShellCommands.START_TESTING_KEY);
        String localize = localizationService.localize(MessageConstants.GREETING,
                DEFAULT_NAME,
                DEFAULT_NAME);
        System.out.println(result);
        System.out.println(localize);
        assertTrue(result.contains(localize));
    }

    @DisplayName("Успешный логин под опредедлённым пользователем")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void successUserLogin() {
        String user = "test";
        String loginCommand = String.format(LOGIN_WITH_CUSTOM_DATA, user, user);
        String result = (String)shell.evaluate(() -> loginCommand);
        String localize = localizationService.localize(MessageConstants.GREETING,
                user,
                user);
        System.out.println(result);
        System.out.println(localize);
        assertTrue(result.contains(localize));
    }

    @DisplayName("Окончание тестирования сразу после логина")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void endTesting() {
        shell.evaluate(() -> QuizShellCommands.START_TESTING_KEY);
        String resultEnd = (String) shell.evaluate(() -> QuizShellCommands.END_TESTING_KEY);
        String localizeFirst = localizationService.localize(MessageConstants.SCORE, "0");
        String localizeSecond = localizationService.localize(MessageConstants.CONGRATULATIONS);
        assertTrue(resultEnd.contains(localizeFirst));
        assertTrue(resultEnd.contains(localizeSecond));
    }

    @DisplayName("Получение вопроса")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void gettingQuestion() {
        shell.evaluate(() -> QuizShellCommands.START_TESTING_KEY);
        String nextQuestion = (String) shell.evaluate(() -> QuizShellCommands.NEXT_QUESTION_KEY);
        assertNotNull(nextQuestion);
    }

    @DisplayName("Чтение ответа пользователя")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void readUserAnswer() {
        String someAnswer = "answer";
        shell.evaluate(() -> QuizShellCommands.START_TESTING_KEY);
        shell.evaluate(() -> QuizShellCommands.NEXT_QUESTION_KEY);
        shell.evaluate(() -> QuizShellCommands.READ_USER_ANSWER_KEY + " " + someAnswer);
        Question questionByNumber = questionService.getQuestionByNumber(1);
        String userAnswer = questionByNumber.getUserAnswer();
        assertEquals(someAnswer, userAnswer);
    }

}