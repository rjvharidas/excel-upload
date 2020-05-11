package com.tle.bootcamp.excelupload.service;

import com.tle.bootcamp.excelupload.builder.ProgramDataBuilder;
import com.tle.bootcamp.excelupload.config.ExcelConfigLoader;
import com.tle.bootcamp.excelupload.exception.ExcelUploadException;
import com.tle.bootcamp.excelupload.model.ExcelField;
import com.tle.bootcamp.excelupload.model.ProgramData;
import com.tle.bootcamp.excelupload.reader.ExcelFileReader;
import com.tle.bootcamp.excelupload.util.Timer;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExcelUploadServiceImpl implements ExcelUploadService {

    @Autowired
    private ExcelFileReader fileReader;

    @Autowired
    private ProgramDataBuilder programDataBuilder;

    @Autowired
    private ExcelConfigLoader excelConfigLoader;

    @Override
    public List processExcel(MultipartFile file) {
        Timer.start();
        ExcelConfigLoader.ExcelConfig excelConfig = getExcelConfig(file.getOriginalFilename());
        Map<Integer, List<ExcelField>> excelRowValues = fileReader
                .getExcelRowValues(getSheets(file).getSheetAt(excelConfig.getSheet()), excelConfig);
        List<ProgramData> programDataList = excelRowValues.values()
                .stream()
                .map(excelFields -> programDataBuilder.getObjectMapping(excelFields, ProgramData.class))
                .collect(Collectors.toList());
        Timer.end();
        return programDataList;
    }

    private ExcelConfigLoader.ExcelConfig getExcelConfig(String fileName) {
        String agency = fileName.split(" ", 2)[0];
        Optional<ExcelConfigLoader.ExcelConfig> config = excelConfigLoader.getConfig(agency);
        return config.orElseThrow(() -> new ExcelUploadException("Excel configuration missing!!."));
    }

    private Workbook getSheets(MultipartFile file) {
        try {
            Path dir = Files.createTempDirectory("");
            File newFile = dir.resolve(file.getOriginalFilename()).toFile();
            file.transferTo(newFile);
            return WorkbookFactory.create(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExcelUploadException("Excel file format is not supported!!.");
        }
    }
}
