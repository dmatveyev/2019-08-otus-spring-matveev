package ru.otus.spring01.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.localization.LocalizationService;
import ru.otus.spring01.localization.MessageConstants;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.spring01.shell.QuizShellCommands.DEFAULT_NAME;

@DisplayName("Shell Commands test")
@SpringBootTest
@ActiveProfiles("test")
class QuizShellCommandsTest {

    @Autowired
    private Shell shell;

    @Autowired
    private LocalizationService localizationService;
    public static final String LOGIN_WITH_CUSTOM_DATA = QuizShellCommands.START_TESTING_KEY + " %s %s";

    @DisplayName("Успешный логин под дефолтным пользователем")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void successDefaultUserLogin() {
        String result = (String)shell.evaluate(() -> QuizShellCommands.START_TESTING_KEY);
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

}