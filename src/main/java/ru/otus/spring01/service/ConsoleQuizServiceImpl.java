package ru.otus.spring01.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dto.Question;
import ru.otus.spring01.dto.UserInfo;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Getter
@Setter
@RequiredArgsConstructor
public class ConsoleQuizServiceImpl implements QuizService {

    private final QuestionDao questionDao;
    private final UserInfoService userInfoService;

    private List<Question> questions;
    private Scanner scanner;

    public void init() {
        questions = questionDao.getQuestions();
        scanner = new Scanner(System.in);
    }

    @Override
    public void testingUser() {Scanner scanner = new Scanner(System.in);
        UserInfo userInfo = userInfoService.extractUserInfo(scanner);
        System.out.println("Hello " + userInfo.getName() + " " + userInfo.getSurname() + "!");
        System.out.println("Please answer for few questions");

        for (Question question : questions) {
            String userAnswer = askQuestion(scanner, question);
            question.setUserAnswer(String.valueOf(userAnswer));
        }
        Random rnd = new Random();
        System.out.println("Your score is: " + (rnd.nextInt(questions.size()) + 1) + "/" + questions.size());
        System.out.println("Thanks for your answers! Goodbye!");
        scanner.close();

    }

    private String askQuestion(Scanner scanner, Question question) {
        List<String> answers = question.getAnswers();
        System.out.println(question.getQuestion());
        String resultAnswer;
        if (answers.size() > 0) {
            for (int i = 0; i < answers.size(); i++) {
                System.out.println(i + 1 + ") " + answers.get(i));
            }
            int userAnswer = getIntUserAnswer(scanner);
            while ((0 >= userAnswer) || userAnswer > (answers.size())) {
                System.out.println("Please choose correct answer!");
                userAnswer = getIntUserAnswer(scanner);
            }
            resultAnswer = String.valueOf(userAnswer);
        } else {
          resultAnswer = getStringUserAnswer(scanner);
        }
        return resultAnswer;
    }

    private int getIntUserAnswer(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Please choose correct answer!");
        }
        String answer = scanner.nextLine();
        return Integer.parseInt(answer);
    }
    private String getStringUserAnswer(Scanner scanner) {
        return scanner.nextLine();
    }

    public void destroy() {
        scanner.close();
    }
}
