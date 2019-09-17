package ru.otus.spring01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring01.dto.UserInfo;
import ru.otus.spring01.localization.LocalizationService;
import ru.otus.spring01.localization.MessageConstants;

@RequiredArgsConstructor
@Component
public class UserInfoServiceImpl implements UserInfoService {

    private final IOService ioService;
    private final LocalizationService localizationService;

    @Override
    public UserInfo readUserInfo() {
        ioService.printString(localizationService.localize(MessageConstants.INPUT_NAME));
        String name = ioService.readString();
        ioService.printString(localizationService.localize(MessageConstants.INPUT_SURNAME));
        String surname = ioService.readString();
        return new UserInfo(name, surname);
    }
}
