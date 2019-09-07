package ru.otus.spring01.service;

import ru.otus.spring01.dto.UserInfo;

import java.util.Scanner;

public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public UserInfo extractUserInfo(Scanner scanner) {
        System.out.print("Input name: ");
        String name = scanner.nextLine();
        System.out.print("Input surname: ");
        String surname = scanner.nextLine();
        return new UserInfo(name, surname);
    }
}
