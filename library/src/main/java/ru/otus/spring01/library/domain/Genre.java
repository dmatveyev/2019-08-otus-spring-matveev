package ru.otus.spring01.library.domain;

import lombok.Getter;

@Getter
public enum Genre {

    FANTASY("fantasy"),
    ACTION("action"),
    DETECTIVE("detective"),
    FAIRY_TAILS("fairy_tails");

    private String code;

    Genre(String code) {
        this.code = code;
    }

    public static Genre getGenreByCode(String code) {
        for (Genre value : Genre.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
