package ru.otus.spring01.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.spring01.dto.Question;
import ru.otus.spring01.config.LocalizationConfiguration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Repository
public class QuestionDaoImpl implements QuestionDao {

    private static final String FILE_NAME_TEMPLATE = "questions_%s.csv";
    private String fileName;

    @Autowired
    public QuestionDaoImpl(LocalizationConfiguration configuration) {
        String currentLanguage = configuration.getCurrentLanguage();
        this.fileName = String.format(FILE_NAME_TEMPLATE, currentLanguage);
    }

    @Override
    @SneakyThrows
    public List<Question> getQuestions() {
        try (InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            return reader.lines()
                    .map(string -> string.split(";"))
                    .map(strings -> {
                        String[] answers = Arrays.copyOfRange(strings, 2, strings.length - 1);
                        return new Question(Integer.parseInt(strings[0]),
                                strings[1],
                                strings[strings.length - 1],
                                answers);
                    })
                    .collect(Collectors.toList());
        }

    }
}
