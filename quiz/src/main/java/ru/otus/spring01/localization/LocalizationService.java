package ru.otus.spring01.localization;

import org.springframework.stereotype.Component;

@Component
public interface LocalizationService {

    String localize(String message, Object... args);
}
