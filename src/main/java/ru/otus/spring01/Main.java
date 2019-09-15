package ru.otus.spring01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring01.service.QuizService;

import java.util.Locale;

@Configuration
@ComponentScan
@PropertySource("application.properties")
public class Main {

    private static ApplicationContext context;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms
                = new ReloadableResourceBundleMessageSource();
        ms.setBasename("localization/app");
        ms.setDefaultEncoding("cp1251");
        return ms;
    }

    @Bean
    public Locale locale(@Value("${language}") String language) {
        return new Locale(language);
    }
    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(Main.class);
        QuizService bean = context.getBean(QuizService.class);
        bean.testingUser();
    }
}
