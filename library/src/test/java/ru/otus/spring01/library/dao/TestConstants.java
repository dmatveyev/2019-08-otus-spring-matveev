package ru.otus.spring01.library.dao;

import java.util.UUID;

public class TestConstants {
    public static final UUID FIRST_GENRE_ID = UUID.fromString("1cc9b82f-b3d5-4b1b-a3f9-eafc32dd6fa9");
    public static final String FIRST_GENRE_NAME = "fantasy";
    public static final String FIRST_GENRE_CODE = "00001";
    public static final UUID SECOND_GENRE_ID = UUID.fromString("67953367-7369-4458-b14c-1456a1d345a2");
    public static final String SECOND_GENRE_NAME = "action";
    public static final String SECOND_GENRE_CODE = "00002";

    public static final UUID FIRST_AUTHOR_ID = UUID.fromString("22063de6-7ae3-46c9-8209-536eb914150a");
    public static final String FIRST_AUTHOR_NAME = "Denis";
    public static final UUID SECOND_AUTHOR_ID = UUID.fromString("150cdadd-790a-4839-9654-9257e1ec0e21");
    public static final String SECOND_AUTHOR_NAME = "Matveev";
    
    public static final UUID PERSON_ID = UUID.fromString("106d3efc-bccd-4e1b-83e0-90022e38d6c2");
    public static final String PERSON_NAME = "admin";
    public static final String PERSON_PASSWORD = "password";

    public static final UUID FIRST_BOOK_ID = UUID.fromString("5bfd646c-87fb-420f-8af1-3694e85e39f0");
    public static final String FIRST_BOOK_NAME = "Some action";
    public static final UUID FIRST_BOOK_AUTHOR_ID = FIRST_AUTHOR_ID;
    public static final UUID FIRST_BOOK_GENRE_ID = FIRST_GENRE_ID;
    public static final String FIRST_BOOK_ISBN = "13-84356-2099345208";

    public static final UUID SECOND_BOOK_ID = UUID.fromString("05d8bdf0-863e-4d80-9dec-0ff333bfa03e");
    public static final String SECOND_BOOK_NAME = "Some fantasy";
    public static final UUID SECOND_BOOK_AUTHOR_ID = FIRST_AUTHOR_ID;
    public static final UUID SECOND_BOOK_GENRE_ID = SECOND_GENRE_ID;
    public static final String SECOND_BOOK_ISBN = "13-84356-1519626998";
    
}
