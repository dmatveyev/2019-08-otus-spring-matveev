package ru.otus.spring01.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring01.config.LocalizationConfiguration;
import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dao.QuestionDaoImpl;

@Configuration
@ActiveProfiles("test")
public class Config {

    @Value("${localization.default-language}")
    private String language;

    @Bean
    public LocalizationConfiguration localizationConfiguration() {
        LocalizationConfiguration localizationConfiguration = new LocalizationConfiguration();
        localizationConfiguration.setDefaultLanguage(language);
        return localizationConfiguration;
    }

    @Bean
    public QuestionDao questionDao() {
        return new QuestionDaoImpl(localizationConfiguration());
    }
}
