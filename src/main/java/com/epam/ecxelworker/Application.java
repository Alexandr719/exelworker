package com.epam.ecxelworker;

import com.epam.ecxelworker.consolidation.ConsolidationWorker;
import com.epam.ecxelworker.file.ExcelFileWorker;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class Application {
    @Autowired
    ExcelFileWorker fileWorker;
    @Autowired
    ConsolidationWorker consolidationWorker;


    public void runConsoleApplication() {
        System.out.println("Выберите действие");
        System.out.println("1 - смержить 2 таблицы в одну");
        System.out.println("2 - транслитерировать поля (имя и фамилия)");

        String filename =
                "D:/Filatov/geekenglish/ecxelworker/src/main" +
                        "/resources/tab1.xlsx";

        String filename2 =
                "D:/Filatov/geekenglish/ecxelworker/src/main" +
                        "/resources/tab2.xlsx";

        XSSFWorkbook xssfWorkbook2 = fileWorker.readExcelBook(filename);
        XSSFWorkbook xssfWorkbook = fileWorker.readExcelBook(filename2);


        fileWorker.saveExcelBook(consolidationWorker
                        .mergeContentIntoTable(xssfWorkbook, xssfWorkbook2),
                "name2.xlsx");


        XSSFSheet myExcelSheet = xssfWorkbook.getSheetAt(0);


        XSSFRow row = myExcelSheet.getRow(0);

    }


}