package com.tle.bootcamp.excelupload.exception;

import com.tle.bootcamp.excelupload.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcelUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ApiErrorResponse> handleException(ExcelUploadException ex) {
        ApiErrorResponse errorDetails = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
                new Date().toString(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        ApiErrorResponse errorDetails = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                new Date().toString(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
