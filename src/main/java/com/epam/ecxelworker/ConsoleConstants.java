package com.epam.ecxelworker;

public class ConsoleConstants {

    public final static int EXIT = 0;
    public final static int MERGE = 1;
    public final static int TRANSLITERATION = 2;

    public final static String CHOOSE_ACTION = "Выберите действие:";

    public final static String EXIT_MESSAGE = " - выход";
    public final static String MERGE_MESSAGE = " - смержить 2 таблицы в одну";
    public final static String TRANSLITERATION_MESSAGE = " - " +
            "транслитерировать поля";

    public final static String ENTET_FULL_PATH = "Введите полный путь до " +
            "файла " +
            "(Пример: D:/Filatov/tab" +
            ".xlsx): ";


    public final static String BYE = "Пока";


    public static final int ZERO = 0;

    public static final String FILE_EXTENSION = ".xlsx";
    public static final String FILE_SAVE = "Введите имя, под каким сохранть " +
            "файл (пример file1):";
    public static final String MERGE_COLUMN_NUMBER =
            "Выберите номер столбца, по которому будет " +
                    "происходить мерж:";
    public static final String ADDITION_COLUMN_NUMBER = "Выберите номер " +
            "столбца, который хотите добавить в таблицу:";


    public static final String FILE_1_PATH_EXTENSION =
            "D:/Filatov/geekenglish/ecxelworker/src/main/resources" +
                    "/tab2.xlsx";

    public static final String FILE_2_PATH_EXTENSION =
            "D:/Filatov/geekenglish/ecxelworker/src/main/resources" +
                    "/tab1.xlsx";

public static final String TRANSLITERATION_COLUMN_NUMBER = "Выберите номер " +
        "столбца, который хотите транслитерировать:";
}