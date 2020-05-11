package com.tle.bootcamp.excelupload.controller;

import com.tle.bootcamp.excelupload.ExcelUploadApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
class ExcelUploadControllerTest extends ExcelUploadApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void uploadExcel() {
//        mockMvc.perform(post("/excel/upload").content()
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.message").value(""));
    }
}