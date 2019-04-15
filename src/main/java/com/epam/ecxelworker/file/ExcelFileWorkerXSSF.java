package com.epam.ecxelworker.file;

import com.epam.ecxelworker.exeptions.ConsoleException;
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

    public XSSFWorkbook readExcelBook(String filePath) throws ConsoleException {
        XSSFWorkbook excelBook;
        try {
            excelBook = new XSSFWorkbook(new FileInputStream
                    (filePath));
        } catch (IOException e) {
            log.error("Error with reading book", e);
            throw new ConsoleException("Неверный путь до файл, возможно файл " +
                    "открыт в другой программе");
        }
        return excelBook;
    }

    public void saveExcelBook(XSSFWorkbook workbook, String fileName)
            throws ConsoleException {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            log.error("Error with saving book", e);
            throw new ConsoleException("Не удалось сохранить файл, возможно " +
                    "файл открыт в другой программе");

        }
    }
}