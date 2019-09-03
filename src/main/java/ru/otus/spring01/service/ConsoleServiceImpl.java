package ru.otus.spring01.service;

import lombok.Getter;
import lombok.Setter;
import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dto.Question;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Getter
@Setter
public class ConsoleServiceImpl implements ConsoleService {

    private QuestionDao questionDao;

    private List<Question> questions;

    @Override
    public void init() {
        questions = questionDao.getQuestions();
    }

    @Override
    public void AskQuestion() {
        try (Scanner scanner = new Scanner(System.in)) {
            getUserInfo(scanner);
            System.out.println("Please answer for few questions");
            for (Question question : questions) {
                List<String> answers = question.getAnswers();
                System.out.println(question.getQuestion());
                for (int i = 0; i < answers.size(); i++) {
                    System.out.println(i + 1 + ") " + answers.get(i));
                }
                int userAnswer = getUserAnswer(scanner);
                while ((0 >= userAnswer) || userAnswer > (answers.size())) {
                    System.out.println("Please choose correct answer!");
                    userAnswer = getUserAnswer(scanner);
                }
            }
            Random rnd  = new Random();
            System.out.println("Your score is: " + (rnd.nextInt(questions.size()) + 1) + "/" + questions.size());
            System.out.println("Thanks for your answers! Goodbye!");
        }
    }

    private void getUserInfo(Scanner scanner) {
        System.out.print("Input name: ");
        String name = scanner.nextLine();
        System.out.print("Input surname: ");
        String surname = scanner.nextLine();
        System.out.println("Hello " + name + " " + surname + "!");
    }

    private int getUserAnswer(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Please choose correct answer!");
        }
        return scanner.nextInt();
    }
}
