package com.epam.ecxelworker.file;

import com.epam.ecxelworker.file.ExcelFileWorker;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Log4j2
public class ExcelFileWorkerXSSF implements ExcelFileWorker {

    public XSSFWorkbook readExcelBook(String filePath) {
        XSSFWorkbook excelBook = null;
        try {
            excelBook = new XSSFWorkbook(new FileInputStream
                    (filePath));
        } catch (IOException e) {
            System.out.println("Неверный путь до файла");
            log.error("Error with reading book", e);
        }
        return excelBook;
    }

    public void saveExcelBook(XSSFWorkbook workbook, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Не удалось сохранить файл");
            log.error("Error with saving book", e);
        }
    }
}