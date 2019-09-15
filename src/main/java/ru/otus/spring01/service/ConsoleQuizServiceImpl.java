package ru.otus.spring01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring01.dto.UserInfo;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class ConsoleQuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final UserInfoService userInfoService;
    private final IOService ioService;
    private final MessageSource messageSource;
    private final Locale currentLocale;


    @Override
    public void testingUser() {
        UserInfo userInfo = userInfoService.readUserInfo();
        ioService.printString(messageSource.getMessage(
                "greeting3", new Object[]{userInfo.getName(), userInfo.getSurname()}, currentLocale));
        ioService.printString(messageSource.getMessage("greeting4", null, currentLocale));

        questionService.askQuestions();
        String score = questionService.getScore();
        ioService.printString(messageSource.getMessage("score", new Object[]{score}, currentLocale));
        ioService.printString(messageSource.getMessage("congratulations", null, currentLocale));
    }


}
