package com.epam.ecxelworker;

import com.epam.ecxelworker.consolidation.ConsolidationWorker;
import com.epam.ecxelworker.file.ExcelFileWorker;
import com.epam.ecxelworker.transliterator.Transliterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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

    @Autowired
    Transliterator transliterator;


    public void runConsoleApplication() {
        System.out.println("Выберите действие");
        System.out.println("1 - смержить 2 таблицы в одну");
        System.out.println("2 - транслитерировать поля ");
        System.out.println("0 - Выход");

        String filename =
                "D:/Filatov/geekenglish/ecxelworker/src/main" +
                        "/resources/tab1.xlsx";

        String filename2 =
                "F:/excelWorker/exelworker/src/main/resources/tab2.xlsx";

        XSSFWorkbook xssfWorkbook = fileWorker.readExcelBook(filename2);
        // XSSFWorkbook xssfWorkbook2 = fileWorker.readExcelBook(filename);


//        String name = "Александер Филатов";
//        System.out.println(transliterator.transliterateField(name));


        int collumNumber = 2;


        XSSFSheet myExcelSheet = xssfWorkbook.getSheetAt(0);



        for (int i = 1; i < 80; i++) {
            XSSFRow row = myExcelSheet.getRow(i);
            Cell cell = row.getCell(collumNumber);
            if (cell.getCellType().equals(CellType.STRING)) {
                cell.setCellValue(transliterator.transliterateField(cell.getStringCellValue()));
            }
        }
        fileWorker.saveExcelBook(xssfWorkbook, "name2.xlsx");


//        fileWorker.saveExcelBook(consolidationWorker
//                        .mergeContentIntoTable(xssfWorkbook, xssfWorkbook2),
//                "name2.xlsx");


    }


}