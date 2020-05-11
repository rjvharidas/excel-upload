package com.tle.bootcamp.excelupload.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tle.bootcamp.excelupload.config.ExcelConfigLoader;
import com.tle.bootcamp.excelupload.constants.FieldType;
import com.tle.bootcamp.excelupload.model.ExcelField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Component
public class ExcelFileReader {

    final static SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");

    public Map<Integer, List<ExcelField>> getExcelRowValues(final Sheet sheet,
                                                            final ExcelConfigLoader.ExcelConfig config) {
        Map<Integer, List<ExcelField>> excelMap = new HashMap<>();
        List<ExcelField> excelSectionHeaders = getExcelHeaderFieldSections(config.getConfigName());
        int totalRows = sheet.getLastRowNum() - config.getRowLimit();
        IntStream.rangeClosed(config.getStartingRow(), totalRows).forEachOrdered(i -> {
            List<ExcelField> excelValues = getExcelData(excelSectionHeaders, sheet.getRow(i));
            excelMap.put(i, excelValues);
        });
        return excelMap;
    }

    private List<ExcelField> getExcelData(List<ExcelField> excelSectionHeaders, Row row) {
        List<ExcelField> excelValues = new ArrayList<>();
        excelSectionHeaders.forEach(excelField -> {
            Cell cell = row.getCell(excelField.getExcelIndex());
            ExcelField exField = getExcelField(excelField, cell);
            excelValues.add(exField);
        });
        return excelValues;
    }

    private ExcelField getExcelField(ExcelField ehc, Cell cell) {
        ExcelField excelField = new ExcelField();
        excelField.setExcelColType(ehc.getExcelColType());
        excelField.setExcelHeader(ehc.getExcelHeader());
        excelField.setExcelIndex(ehc.getExcelIndex());
        excelField.setPojoAttribute(ehc.getPojoAttribute());
        addField(cell, excelField, ehc.getExcelColType());
        return excelField;
    }

    private void addField(Cell cell, ExcelField excelField, String cellType) {
        if (FieldType.STRING.getValue().equalsIgnoreCase(cellType)) {
            excelField.setExcelValue(cell.getStringCellValue());
        } else if (FieldType.DOUBLE.getValue().equalsIgnoreCase(cellType)
                || FieldType.INTEGER.getValue().equalsIgnoreCase(cellType)) {
            excelField.setExcelValue(String.valueOf(cell.getNumericCellValue()));
        } else if (DateUtil.isCellDateFormatted(cell)) {
            excelField.setExcelValue(dtf.format(cell.getDateCellValue()));
        }
    }

    private List<ExcelField> getExcelHeaderFieldSections(String jsonFile) {
        List<ExcelField> jsonList = null;
        try {
            String jsonConfig = new String(
                    Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("config/" + jsonFile).toURI())));
            jsonList = new ObjectMapper().readValue(jsonConfig, new TypeReference<List<ExcelField>>() {
            });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return jsonList;
    }
}
