package ru.otus.spring01.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.dto.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class QuestionDaoImpl implements QuestionDao {

    private final MessageSource messageSource;
    private String fileName;

    @Autowired
    public QuestionDaoImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.fileName = messageSource.getMessage("fileName", null, new Locale("ru"));
    }

    @Override
    @SneakyThrows
    public List<Question> getQuestions() {
        try (InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            return reader.lines()
                    .map(string -> string.split(";"))
                    .map(strings -> {
                        String[] answers = Arrays.copyOfRange(strings, 1, strings.length - 1);
                        return new Question(strings[0], strings[strings.length - 1], answers);
                    })
                    .collect(Collectors.toList());
        }

    }
}
