package ru.otus.spring01.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring01.dto.UserInfo;

@RequiredArgsConstructor
public class ConsoleQuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final UserInfoService userInfoService;
    private final IOService ioService;


    @Override
    public void testingUser() {
        UserInfo userInfo = userInfoService.readUserInfo();
        ioService.printString("Hello " + userInfo.getName() + " " + userInfo.getSurname() + "!");
        ioService.printString("Please answer for few questions");

        questionService.askQuestions();
        String score = questionService.getScore();
        ioService.printString("Your score is: " + score);
        ioService.printString("Thanks for your answers! Goodbye!");
    }


}
