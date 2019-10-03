package ru.otus.spring01.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring01.dto.Question;
import ru.otus.spring01.dto.UserInfo;
import ru.otus.spring01.localization.LocalizationService;
import ru.otus.spring01.localization.MessageConstants;
import ru.otus.spring01.service.IOService;
import ru.otus.spring01.service.QuestionService;
import ru.otus.spring01.service.UserInfoService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class QuizShellCommands {

    private static final String NEXT_QUESTION_KEY = "next";
    private static final String READ_USER_ANSWER_KEY= "answer:";
    private final QuestionService questionService;
    private final UserInfoService userInfoService;
    private final IOService ioService;
    private final LocalizationService localizationService;
    private Question currentQuestion;
    private boolean isStarting = false;

    @ShellMethod("Start testing")
    public void start() {
        if (!isStarting) {
            isStarting = true;
            testingUser();
        }
    }

    @ShellMethod(key = NEXT_QUESTION_KEY, value = "Get next question")
    public String getNextQuestion() {
        Question question = questionService.getNextQuestion();
        if (question == null) {
            return endTesting();
        }
        currentQuestion = question;
        StringBuilder sb = new StringBuilder(question.getQuestion());
        List<String> answers = question.getAnswers();
        if (!answers.isEmpty()) {
            for (int i = 1; i < answers.size() + 1; i++) {
                sb.append(System.lineSeparator());
                sb.append(i).append(") ").append(answers.get(i - 1));
            }
        }
        return sb.toString();
    }

    @ShellMethod(key = READ_USER_ANSWER_KEY, value = "Read user answer")
    public void readUserAnswer(String answer) {
        currentQuestion.setUserAnswer(answer);
    }

    private void testingUser() {
        UserInfo userInfo = userInfoService.readUserInfo();
        ioService.printString(localizationService.localize(
                MessageConstants.GREETING, userInfo.getName(), userInfo.getSurname()));
        ioService.printString(localizationService.localize(MessageConstants.START_TESTING));
        ioService.printString(getNextQuestion());
    }

    private String endTesting() {
        isStarting = false;
        StringBuilder sb = new StringBuilder();
        String score = questionService.getScore();
        sb.append(localizationService.localize(MessageConstants.SCORE, score));
        sb.append(System.lineSeparator());
        sb.append(localizationService.localize(MessageConstants.CONGRATULATIONS));
        return sb.toString();
    }

    @ShellMethodAvailability({NEXT_QUESTION_KEY, READ_USER_ANSWER_KEY})
    public Availability availabilityOnStartTesting() {
        return isStarting
                ? Availability.available()
                : Availability.unavailable("Testing is not starting");
    }

}
