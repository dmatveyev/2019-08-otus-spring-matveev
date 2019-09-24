package ru.otus.spring01.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private Locale currentLocale;
    private MessageSource messageSource;

    @Autowired
    public LocalizationServiceImpl(LocalizationConfiguration localizationConfiguration,
                                   MessageSource messageSource) {
        this.currentLocale = new Locale(localizationConfiguration.getCurrentLanguage());
        this.messageSource = messageSource;
    }

    @Override
    public String localize(String message, Object... args) {
        return messageSource.getMessage(
                message, args, currentLocale);
    }
}
