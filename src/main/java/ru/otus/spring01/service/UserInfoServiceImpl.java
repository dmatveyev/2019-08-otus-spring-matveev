package ru.otus.spring01.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring01.dto.UserInfo;

@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final IOService ioService;

    @Override
    public UserInfo extractUserInfo() {
        ioService.printString("Input name: ");
        String name = ioService.readString();
        ioService.printString("Input surname: ");
        String surname = ioService.readString();
        return new UserInfo(name, surname);
    }
}
