package com.epam.ecxelworker;

import com.epam.ecxelworker.consolidation.ConsolidationWorker;
import com.epam.ecxelworker.file.ExcelFileWorker;
import com.epam.ecxelworker.transliterator.Transliterator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Scanner;

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
            case ConsoleConstants.EXIT:
                System.out.println(ConsoleConstants.BYE);
                break;
            default:
                break;
        }


        String filename =
                "D:/Filatov/geekenglish/ecxelworker/src/main" +
                        "/resources/tab1.xlsx";

        String filename2 =
                "D:/Filatov/geekenglish/ecxelworker/src/main" +
                        "/resources/tab2.xlsx";


        XSSFWorkbook xssfWorkbook = fileWorker.readExcelBook(filename2);
        // XSSFWorkbook xssfWorkbook2 = fileWorker.readExcelBook(filename);


        XSSFSheet myExcelSheet = xssfWorkbook.getSheetAt(0);


        transliterator.transliterateExcelCollumn(myExcelSheet, 2);
        fileWorker.saveExcelBook(xssfWorkbook, "name2.xlsx");


//        fileWorker.saveExcelBook(consolidationWorker
//                        .mergeContentIntoTable(xssfWorkbook, xssfWorkbook2),
//                "name2.xlsx");
    }


    private void showEvents() {
        System.out.println(ConsoleConstants.CHOOSE_ACTION);
        System.out.println(
                ConsoleConstants.MERGE + ConsoleConstants.MERGE_MESSAGE);
        System.out.println(ConsoleConstants.TRANSLITERATION +
                ConsoleConstants.TRANSLITERATION_MESSAGE);
        System.out
                .println(ConsoleConstants.EXIT + ConsoleConstants.EXIT_MESSAGE);

    }

    private void startMerge() {

        //Введите полный путь до 1 файла
        String mainFile =
                "D:/Filatov/geekenglish/ecxelworker/src/main" +
                        "/resources/tab2.xlsx";

        //Введите номер листа начиная с 0
        XSSFWorkbook xssfWorkbook = fileWorker.readExcelBook(mainFile);
        XSSFSheet myExcelSheet = xssfWorkbook.getSheetAt(0);

        //Введите полный путь до 2 файла
        String mergeFile =
                "D:/Filatov/geekenglish/ecxelworker/src/main" +
                        "/resources/tab1.xlsx";

        //Введите номер листа второго файла начиная с 0
        XSSFWorkbook xssfWorkbook2 = fileWorker.readExcelBook(mergeFile);
        XSSFSheet myExcelSheet2 = xssfWorkbook2.getSheetAt(0);

        //Выберите номер столбца, который хотите добавить в таблицу
        showTableHeader(myExcelSheet2);
        System.out.println("Выберите номер столбца, который хотите добавить в таблицу");
        int additionNumber = 2;
        System.out.println("Выберите номер столбца, по которому будет " +
                "происходить мерж:");
        int mergingNumber = 1;

        showTableHeader(myExcelSheet);
        System.out.println("Выберите номер столбца, по которому будет " +
                "происходить мерж:");
        int mainMergingNumber = 1;


        consolidationWorker.mergeContentIntoTable
                (myExcelSheet, myExcelSheet2);
        //Введите имя, под каким сохранть файл
        fileWorker.saveExcelBook(xssfWorkbook, "merge.xlsx");


    }

    private void startTransliteration() {


    }

    private void showTableHeader(XSSFSheet sheet) {

        System.out.println("##########################");
        XSSFRow row = sheet.getRow(ConsoleConstants.ZERO);
        Iterator cellIter = row.cellIterator();
        int counter = 0;
        while (cellIter.hasNext()) {
            XSSFCell cell = (XSSFCell) cellIter.next();
            System.out.println(counter + "-" + cell.getStringCellValue());
            counter++;
        }
        System.out.println("##########################");

    }

}