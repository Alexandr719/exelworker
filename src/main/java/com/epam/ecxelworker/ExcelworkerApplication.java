package com.epam.ecxelworker;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Scanner;

@Log4j2
@SpringBootApplication
public class ExcelworkerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ExcelworkerApplication.class, args);

    }

    @Override
    public void run(String... args) {

        System.out.println("Выберите действие");
        System.out.println("1 - смержить 2 таблицы в одну");
        System.out.println("2 - транслитерировать поля (имя и фамилия)");

//        Scanner in = new Scanner(System.in);
//        System.out.print("Введите путь до файла : ");
//        String filePath = in.nextLine();


        try {
            String filename =
                    "D:/Filatov/geekenglish/ecxelworker/src/main" +
                            "/resources/tab1.xlsx";

            String filename2 =
                    "D:/Filatov/geekenglish/ecxelworker/src/main" +
                            "/resources/tab2.xlsx";
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream
                    (filename));
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

           //Получаем header
            XSSFRow row = myExcelSheet.getRow(0);
            Iterator cellIter = row.cellIterator();
            while (cellIter.hasNext()) {
                XSSFCell cell = (XSSFCell) cellIter.next();
                System.out.println(cell);
            }

        } catch (Exception e) {
            log.error("Error");
        }


    }
}
