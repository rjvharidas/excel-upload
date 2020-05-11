package com.tle.bootcamp.excelupload.controller;

import com.tle.bootcamp.excelupload.service.ExcelUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ExcelUploadController {

    @Autowired
    private ExcelUploadService excelUploadService;

    @PostMapping("/excel/upload")
    public List<Object> uploadExcel(@RequestParam("file") MultipartFile file) {
        return excelUploadService.processExcel(file);
    }
}
