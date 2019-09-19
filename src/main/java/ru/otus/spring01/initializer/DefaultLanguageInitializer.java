package ru.otus.spring01.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

public class DefaultLanguageInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String property = applicationContext.getEnvironment().getProperty("localization.default-language");
        boolean aNull = Objects.isNull(property);
        if (aNull) {
            throw new RuntimeException("Default language is not set");
        }
    }
}
