package ru.otus.spring01.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocalizationServiceImpl implements LocalizationService {

    private Locale currentLocale;
    private MessageSource messageSource;

    @Autowired
    public LocalizationServiceImpl(@Value("${language}") String currentLanguage,
                                   MessageSource messageSource) {
        this.currentLocale = new Locale(currentLanguage);
        this.messageSource = messageSource;
    }

    @Override
    public String localize(String message, Object... args) {
        return messageSource.getMessage(
                message, args, currentLocale);
    }
}
