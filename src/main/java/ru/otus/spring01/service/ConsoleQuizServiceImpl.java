package ru.otus.spring01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.spring01.dto.UserInfo;
import ru.otus.spring01.localization.LocalizationService;
import ru.otus.spring01.localization.MessageConstants;

@RequiredArgsConstructor
@Component
public class ConsoleQuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final UserInfoService userInfoService;
    private final IOService ioService;
    private final LocalizationService localizationService;

    @Override
    @EventListener(ApplicationStartedEvent.class)
    public void testingUser() {
        UserInfo userInfo = userInfoService.readUserInfo();
        ioService.printString(localizationService.localize(
                MessageConstants.GREETING, userInfo.getName(), userInfo.getSurname()));
        ioService.printString(localizationService.localize(MessageConstants.START_TESTING));

        questionService.askQuestions();
        String score = questionService.getScore();
        ioService.printString(localizationService.localize(MessageConstants.SCORE, score));
        ioService.printString(localizationService.localize(MessageConstants.CONGRATULATIONS));
    }


}
