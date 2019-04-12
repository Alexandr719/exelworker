package com.epam.ecxelworker.consolidation;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@Log4j2
@Service
public class ConsolidationWorker {


     public void mergeContentIntoTable(XSSFSheet myExcelSheet,
                                              XSSFSheet myExcelSheet2, int
                                               compareCellNumberOne, int
                                               compareCellNumberTwo, int
                                       additionCellNumber
                                             ) {


        Map<String, Integer> firstTableMap = createRowIndexEmailMap
                (myExcelSheet, compareCellNumberOne);
        Map<String, Integer> secondTableMap =
                createRowIndexEmailMap(myExcelSheet2, compareCellNumberTwo);

        XSSFRow outRow = myExcelSheet.getRow(additionCellNumber);
        int lastNumber = outRow.getLastCellNum();

        for (String key : firstTableMap.keySet()) {
            for (String key2 : secondTableMap.keySet()) {
                if (key.equalsIgnoreCase(key2)) {
                    int currentRowNumber = firstTableMap.get(key);
                    int tableTwoCurrentRowNumber = secondTableMap.get(key2);

                    mergeNewColumn(myExcelSheet, myExcelSheet2,
                            currentRowNumber, lastNumber,
                            tableTwoCurrentRowNumber, additionCellNumber);
                }
            }
        }

    }


    private Map<String, Integer> createRowIndexEmailMap(XSSFSheet sheet, int
            compareCellNumber) {
        Map<String, Integer> tableMap = new HashMap<>();

        Iterator secondRowIter = sheet.rowIterator();
        while (secondRowIter.hasNext()) {
            XSSFRow currentRow = (XSSFRow) secondRowIter.next();
            tableMap.put(currentRow.getCell(compareCellNumber)
                    .getStringCellValue(), currentRow.getRowNum());
            if (currentRow.getCell(compareCellNumber).getStringCellValue()
                    .equals("")) {
                break;
            }
        }


        return tableMap;

    }

    private void mergeNewColumn(XSSFSheet editExcelSheet, XSSFSheet
            resourceExcelSheet, int editExcelRowNumber, int editLastColumnNumber,
                                int resourceRowNumber, int resourceCellNumber) {
        XSSFRow editRow = editExcelSheet.getRow(editExcelRowNumber);
        Cell editCell = editRow.createCell(editLastColumnNumber);
        XSSFRow resourceRow = resourceExcelSheet.getRow
                (resourceRowNumber);
        Cell resourceCell = resourceRow.getCell(resourceCellNumber);
        editCell.setCellValue(resourceCell.getStringCellValue());
    }


}