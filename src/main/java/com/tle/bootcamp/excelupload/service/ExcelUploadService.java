package com.tle.bootcamp.excelupload.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelUploadService {
    List<Object> processExcel(MultipartFile file);
}
