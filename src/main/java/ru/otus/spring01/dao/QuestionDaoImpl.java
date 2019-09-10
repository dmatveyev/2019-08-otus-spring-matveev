package ru.otus.spring01.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.otus.spring01.dto.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private String fileName;

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
