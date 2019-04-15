package com.epam.ecxelworker;

import com.epam.ecxelworker.consolidation.ConsolidationWorker;
import com.epam.ecxelworker.exeptions.ConsoleException;
import com.epam.ecxelworker.file.ExcelFileWorker;
import com.epam.ecxelworker.transliterator.Transliterator;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Scanner;

@Log4j2
@Service
public class Application {

    @Autowired
    ExcelFileWorker fileWorker;
    @Autowired
    ConsolidationWorker consolidationWorker;
    @Autowired
    Transliterator transliterator;


    void runConsoleApplication() {
        showEvents();
        Scanner in = new Scanner(System.in);
        System.out.print("Введите номер:");
        int num = in.nextInt();
        switch (num) {
            case ConsoleConstants.MERGE:
                System.out
                        .println("Вы выбрали" + ConsoleConstants.MERGE_MESSAGE);
                startMerge();
                break;
            case ConsoleConstants.TRANSLITERATION:
                System.out.println("Вы выбрали" +
                        ConsoleConstants.TRANSLITERATION_MESSAGE);
                startTransliteration();
                break;
            case ConsoleConstants.DEFAULTRUN:
                System.out.println("Вы выбрали" +
                        ConsoleConstants.DEFAULTRUN_MESSAGE);
               stratDefaultTransliterAndMegre();
                break;
            case ConsoleConstants.EXIT:
                System.out.println(ConsoleConstants.BYE);
                break;
            default:
                break;
        }
        System.out.println("Нажмите любую клавишу, чтобы выйти");
        try {
           System.in.read();
            System.out.println(ConsoleConstants.BYE);
        } catch (IOException e) {
            System.out.println("Что то пошло не так");
        }
    }

    private void stratDefaultTransliterAndMegre() {
        Scanner in = new Scanner(System.in);
        //Read first file
        System.out.print(ConsoleConstants.ENTET_FULL_PATH);
        String mainFile = in.nextLine();

        XSSFWorkbook xssfWorkbook = null;
        XSSFSheet myExcelSheet = null;
        try {
            xssfWorkbook = fileWorker.readExcelBook(mainFile);
            myExcelSheet = xssfWorkbook.getSheetAt(0);
        } catch (ConsoleException e) {
            System.out.println(e.getMessage());
        }

        //Read second file
        System.out.print(ConsoleConstants.ENTET_FULL_PATH);
        String mergeFile = in.nextLine();
//        String mergeFile =
//                ConsoleConstants.FILE_2_PATH_EXTENSION;
        XSSFWorkbook xssfWorkbook2;
        XSSFSheet myExcelSheet2 = null;
        try {
            xssfWorkbook2 = fileWorker.readExcelBook(mergeFile);
            myExcelSheet2 = xssfWorkbook2.getSheetAt(0);
        } catch (ConsoleException e) {
            System.out.println(e.getMessage());
        }
        log.info("All files prepared  for merge");

        consolidationWorker.mergeContentIntoTable
                (myExcelSheet, myExcelSheet2, 1,
                        1, 2);
        transliterator.transliterateExcelColumn(myExcelSheet, 2);
        saveFileByInputName(xssfWorkbook);

    }


    private void showEvents() {
        System.out.println(ConsoleConstants.APP_NAME);
        System.out.println("\n");
        System.out.println(ConsoleConstants.CHOOSE_ACTION);
        System.out.println(
                ConsoleConstants.MERGE + ConsoleConstants.MERGE_MESSAGE);
        System.out.println(ConsoleConstants.TRANSLITERATION +
                ConsoleConstants.TRANSLITERATION_MESSAGE);
        System.out
                .println(ConsoleConstants.DEFAULTRUN + ConsoleConstants
                        .DEFAULTRUN_MESSAGE);
        System.out
                .println(ConsoleConstants.EXIT + ConsoleConstants.EXIT_MESSAGE);

    }

    private void startMerge() {
        log.info("Start merge");
        Scanner in = new Scanner(System.in);

        //Read first file
        System.out.print(ConsoleConstants.ENTET_FULL_PATH);
        String mainFile = in.nextLine();
       // String mainFile = ConsoleConstants.FILE_1_PATH_EXTENSION;
        XSSFWorkbook xssfWorkbook = null;
        XSSFSheet myExcelSheet = null;
        try {
            xssfWorkbook = fileWorker.readExcelBook(mainFile);
            myExcelSheet = xssfWorkbook.getSheetAt(0);
        } catch (ConsoleException e) {
            System.out.println(e.getMessage());
        }

        //Read second file
        System.out.print(ConsoleConstants.ENTET_FULL_PATH);
        String mergeFile = in.nextLine();
//        String mergeFile =
//                ConsoleConstants.FILE_2_PATH_EXTENSION;
        XSSFWorkbook xssfWorkbook2 = null;
        try {
            xssfWorkbook2 = fileWorker.readExcelBook(mergeFile);
        } catch (ConsoleException e) {
            System.out.println(e.getMessage());
        }
        XSSFSheet myExcelSheet2 = xssfWorkbook2.getSheetAt(0);


        log.info("All files prepared  for merge");


        //Conditions
        showTableHeader(myExcelSheet2);
        System.out.println(
                ConsoleConstants.ADDITION_COLUMN_NUMBER);
        int additionNumber = in.nextInt();
       // int additionNumber = 2;
        System.out.println(ConsoleConstants.MERGE_COLUMN_NUMBER);
        int mergingNumber = in.nextInt();
        //int mergingNumber = 1;

        showTableHeader(myExcelSheet);
        System.out.println(ConsoleConstants.MERGE_COLUMN_NUMBER);
        int mainMergingNumber = in.nextInt();
       // int mainMergingNumber = 1;

        consolidationWorker.mergeContentIntoTable
                (myExcelSheet, myExcelSheet2, mainMergingNumber,
                        mergingNumber, additionNumber);

        log.info("Files  merged");
        saveFileByInputName(xssfWorkbook);

    }

    private void startTransliteration() {
        //Введите полный путь до 1 файла
        log.info("Start transliteration");
        Scanner in = new Scanner(System.in);

        //Read first file
        System.out.print(ConsoleConstants.ENTET_FULL_PATH);
        String mainFile = in.nextLine();
//        String mainFile =
//                "D:/Filatov/geekenglish/ecxelworker/src/main" +
//                        "/resources/tab2.xlsx";


        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = fileWorker.readExcelBook(mainFile);
        } catch (ConsoleException e) {
            System.out.println(e.getMessage());
        }
        XSSFSheet myExcelSheet = xssfWorkbook.getSheetAt(0);
        log.info("File prepared  for transliteration");

        //Выберите номер столбца, который хотите транслитерировать
        showTableHeader(myExcelSheet);
        System.out.print(ConsoleConstants.TRANSLITERATION_COLUMN_NUMBER);
        int columnNumber = in.nextInt();
        //int columnNumber = 2;

        transliterator.transliterateExcelColumn(myExcelSheet, columnNumber);
        log.info("File transliterated");
        saveFileByInputName(xssfWorkbook);

    }

    private void saveFileByInputName(XSSFWorkbook workbook) {
        //Введите имя, под каким сохранть файл
        Scanner in = new Scanner(System.in);
        System.out.println(ConsoleConstants.FILE_SAVE);
        String fileName = in.nextLine();
        fileName += ConsoleConstants.FILE_EXTENSION;
        try {
            fileWorker.saveExcelBook(workbook, fileName);
        } catch (ConsoleException e) {
            System.out.println(e.getMessage());
        }
        log.info("File was saving with name " + fileName);
    }

    private void showTableHeader(XSSFSheet sheet) {
        System.out.println("##########################");
        XSSFRow row = sheet.getRow(ConsoleConstants.ZERO);
        Iterator cellIter = row.cellIterator();
        int counter = 0;
        while (cellIter.hasNext()) {
            XSSFCell cell = (XSSFCell) cellIter.next();
            System.out.println(counter + " - " + cell.getStringCellValue());
            counter++;
        }
        System.out.println("##########################");
    }

}