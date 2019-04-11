package com.epam.ecxelworker.file;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ExcelFileWorker {

    XSSFWorkbook readExcelBook(String filePath);
    void saveExcelBook(XSSFWorkbook workbook, String fileName);
}