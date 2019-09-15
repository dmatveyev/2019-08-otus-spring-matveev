package ru.otus.spring01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring01.dto.UserInfo;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class UserInfoServiceImpl implements UserInfoService {

    private final IOService ioService;
    private final MessageSource messageSource;
    private final Locale currentLocale;


    @Override
    public UserInfo readUserInfo() {
        ioService.printString(messageSource.getMessage("greeting1", null, currentLocale));
        String name = ioService.readString();
        ioService.printString(messageSource.getMessage("greeting2", null, currentLocale));
        String surname = ioService.readString();
        return new UserInfo(name, surname);
    }
}
