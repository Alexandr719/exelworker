package com.epam.ecxelworker.transliterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Transliterator {

    @Autowired
    @Qualifier("namesProperty")
    private Map<String, String> namesMap;

    @Autowired
    @Qualifier("lettersProperty")
    private Map<String, String> lettersMap;


    /**
     * Function for transliterate  field  sheet by columnNumber
     */
    public void transliterateExcelColumn(XSSFSheet sheet, int columnNumber) {
        XSSFRow firstRoll = sheet.getRow(0);
        int maxCellNumber = firstRoll.getLastCellNum();
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            XSSFRow row = sheet.getRow(i);
            XSSFCell newCell = row.createCell(maxCellNumber);
            Cell cell = row.getCell(columnNumber);
            if (cell.getCellType().equals(CellType.STRING)) {
                newCell.setCellValue(
                        transliterateField(cell.getStringCellValue()));
                divideColumn(sheet, i, maxCellNumber);
            } else {
                System.out.println("Обнаружен неверный тип поля строка номер " +
                        "" + i);
            }
        }
    }

    /**
     * Function for transliterate field (Few words)
     */
    private String transliterateField(String field) {
        StringBuilder outWord = new StringBuilder("");
        if (!field.equals("")) {
            field = field.trim();
            String[] words = field.split("\\s");
            for (String subStr : words) {
                outWord.append(transliterateWord(subStr)).append(" ");
            }
        }
        return outWord.toString();
    }

    /**
     * Function for transliterate word
     */
    private String transliterateWord(String word) {
        word = word.toLowerCase();
        String outWord = null;
        boolean findName = false;
        for (String key : namesMap.keySet()) {
            if (key.equals(word)) {
                outWord = namesMap.get(key);
                findName = true;
                break;
            }
        }
        if (!findName) {
            char[] chars = word.toCharArray();
            outWord = transliterateChars(chars);
        }
        return outWord.substring(0, 1).toUpperCase() + outWord.substring(1);
    }

    /**
     * Function for transliterate chars array
     */
    private String transliterateChars(char[] chars) {
        StringBuilder outWord = new StringBuilder("");
        boolean findLetter = false;
        for (char aChar : chars) {
            for (String key : lettersMap.keySet()) {
                if (key.charAt(0) == aChar) {
                    outWord.append(lettersMap.get(key));
                    findLetter = true;
                }
            }
            if (!findLetter) {
                outWord.append(aChar);
            }
        }
        return outWord.toString();
    }

    /**
     * Function for devide one column to two
     * */
    private void divideColumn(XSSFSheet sheet, int rowId, int columnId) {
        XSSFRow row = sheet.getRow(rowId);
        XSSFCell cell = row.getCell(columnId);
        if (cell.getCellType().equals(CellType.STRING) && !cell
                .getStringCellValue().equals("")) {
            String cellValue = cell.getStringCellValue();
            String[] words = cellValue.split("\\s");
            cell.setCellValue(words[0]);
            XSSFCell newCell = row.createCell(columnId + 1);
            if(words.length == 1){
                System.out.println("Поле с одним словом "+ cellValue);
            }
            else if (words.length == 2) {
                newCell.setCellValue(words[1]);
            } else {
                System.out.println("Поле с 3 и более словами:" +
                        cellValue);
                StringBuilder out = new StringBuilder();
                for (int i = 1; i < words.length; i++) {
                    out.append(words[i]);
                    out.append(" ");
                }
                newCell.setCellValue(out.toString());
            }
        }

    }


}