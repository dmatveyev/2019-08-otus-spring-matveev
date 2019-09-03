package ru.otus.spring01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring01.service.ConsoleService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ConsoleService bean = context.getBean(ConsoleService.class);
        bean.AskQuestion();
    }
}
