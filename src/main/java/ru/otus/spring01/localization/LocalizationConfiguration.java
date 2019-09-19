package ru.otus.spring01.localization;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class LocalizationConfiguration {

    private final String defaultLanguage;

    private String currentLanguage;

    public LocalizationConfiguration(@Value("${language}")String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
        this.currentLanguage = defaultLanguage;
    }
}
