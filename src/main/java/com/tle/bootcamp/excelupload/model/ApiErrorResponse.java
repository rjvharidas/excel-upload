package com.tle.bootcamp.excelupload.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApiErrorResponse {
    private HttpStatus status;
    private String timestamp;
    private String message;
}
