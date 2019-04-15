package com.epam.ecxelworker.file;

import com.epam.ecxelworker.exeptions.ConsoleException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ExcelFileWorker {

    XSSFWorkbook readExcelBook(String filePath) throws ConsoleException;
    void saveExcelBook(XSSFWorkbook workbook, String fileName) throws ConsoleException;
}