package com.epam.ecxelworker.transliterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
     *      */
    public void transliterateExcelColumn(XSSFSheet sheet, int columnNumber){
        for (int i = 1; i < sheet.getLastRowNum()+1 ; i++) {
            XSSFRow row = sheet.getRow(i);
            Cell cell = row.getCell(columnNumber);
            if (cell.getCellType().equals(CellType.STRING)) {
                cell.setCellValue(transliterateField(cell.getStringCellValue()));
            }
        }
    }

    /**
     * Function for transliterate field (Few words)
     *      */
    public String transliterateField(String field) {
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
     *      */
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
     *      */
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


}