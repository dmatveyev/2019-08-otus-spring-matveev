package ru.otus.spring01.localization;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("localization")
public class LocalizationConfiguration {

    private String defaultLanguage;

    private String currentLanguage;

    public String getCurrentLanguage() {
        return  currentLanguage == null ? defaultLanguage: currentLanguage;
    }
}
