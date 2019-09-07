package ru.otus.spring01.service;

import ru.otus.spring01.dto.UserInfo;

import java.util.Scanner;

public interface UserInfoService {

    UserInfo extractUserInfo(Scanner scanner);
}
