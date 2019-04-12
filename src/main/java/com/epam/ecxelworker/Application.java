package com.epam.ecxelworker;

import com.epam.ecxelworker.consolidation.ConsolidationWorker;
import com.epam.ecxelworker.file.ExcelFileWorker;
import com.epam.ecxelworker.transliterator.Transliterator;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            case ConsoleConstants.EXIT:
                System.out.println(ConsoleConstants.BYE);
                break;
            default:
                break;
        }

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
        log.info("Start merge");

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

        log.info("All files prepared  for merge");

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
                (myExcelSheet, myExcelSheet2, mainMergingNumber,
                        mergingNumber , additionNumber);

        log.info("Files  merged");
        saveFileByInputName(xssfWorkbook);

    }

    private void startTransliteration() {
        //Введите полный путь до 1 файла
        log.info("Start transliteration");
        String mainFile =
                "D:/Filatov/geekenglish/ecxelworker/src/main" +
                        "/resources/tab2.xlsx";

        //Введите номер листа начиная с 0
        XSSFWorkbook xssfWorkbook = fileWorker.readExcelBook(mainFile);
        XSSFSheet myExcelSheet = xssfWorkbook.getSheetAt(0);
        log.info("File prepared  for transliteration");
        //Выберите номер столбца, который хотите транслитерировать
        showTableHeader(myExcelSheet);
        int columnNumber = 2;

        transliterator.transliterateExcelColumn(myExcelSheet, columnNumber);
        log.info("File transliterated");
        saveFileByInputName(xssfWorkbook);

    }

    private void saveFileByInputName(XSSFWorkbook workbook){
        //Введите имя, под каким сохранть файл
        String fileName = "merge";
        fileName += ConsoleConstants.FILE_EXTENSION;
        fileWorker.saveExcelBook(workbook, fileName);
        log.info("File was saving with name "+ fileName);
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