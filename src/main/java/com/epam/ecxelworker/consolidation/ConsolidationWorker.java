package com.epam.ecxelworker.consolidation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class ConsolidationWorker {

    private final static int EMAIL_CELL_NUMBER = 1; //номер ячейки с Email
    private final static int HEADER_ROW = 0; //номер ячейки с Email
    private final static int PRISHEL_ROW = 2; //номер ячейки с Email

    private XSSFWorkbook mergeHeaderIntoTable(XSSFWorkbook workbook, String
            header) {
        XSSFSheet myExcelSheet = workbook.getSheetAt(0);
        XSSFRow row = myExcelSheet.getRow(0);
        Cell name = row.createCell(row.getLastCellNum());
        name.setCellValue(header);
        return workbook;
    }

    public XSSFWorkbook mergeContentIntoTable(XSSFWorkbook workbook,
                                              XSSFWorkbook workbook2

                                             ) {
        XSSFSheet myExcelSheet = workbook.getSheetAt(0);
        XSSFSheet myExcelSheet2 = workbook2.getSheetAt(0);

        XSSFRow row = myExcelSheet.getRow(HEADER_ROW);
        Cell cell = row.getCell(EMAIL_CELL_NUMBER);
        System.out.println("1 таблица " + cell.getStringCellValue());


        XSSFRow row2 = myExcelSheet2.getRow(HEADER_ROW);
        Cell cell2 = row2.getCell(PRISHEL_ROW);
        System.out.println("2 таблица " + cell2.getStringCellValue());

        Map<String, Integer> firstTableMap = createRowIndexEmailMap
                (myExcelSheet);
        Map<String, Integer> secondTableMap =
                createRowIndexEmailMap(myExcelSheet2);


        XSSFRow outRow = myExcelSheet.getRow(2);
        int lastNumber = outRow.getLastCellNum();

        for (String key : firstTableMap.keySet()) {
            for (String key2 : secondTableMap.keySet()) {
                if (key.equalsIgnoreCase(key2)) {
                    int currentRowNumber = firstTableMap.get(key);
                    int tableTwoCurrentRowNumber = secondTableMap.get(key2);

                    mergeNewColumn(myExcelSheet, myExcelSheet2,
                            currentRowNumber, lastNumber,
                            tableTwoCurrentRowNumber, PRISHEL_ROW);
                }
            }
        }
        return workbook;
    }


    private Map<String, Integer> createRowIndexEmailMap(XSSFSheet sheet) {
        Map<String, Integer> tableMap = new HashMap<>();

        Iterator secondRowIter = sheet.rowIterator();
        while (secondRowIter.hasNext()) {
            XSSFRow currentRow = (XSSFRow) secondRowIter.next();
            tableMap.put(currentRow.getCell(EMAIL_CELL_NUMBER)
                    .getStringCellValue(), currentRow.getRowNum());
            if (currentRow.getCell(EMAIL_CELL_NUMBER).getStringCellValue()
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