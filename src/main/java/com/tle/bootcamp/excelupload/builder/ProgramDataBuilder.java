package com.tle.bootcamp.excelupload.builder;

import com.tle.bootcamp.excelupload.constants.FieldType;
import com.tle.bootcamp.excelupload.model.ExcelField;
import com.tle.bootcamp.excelupload.model.ProgramData;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProgramDataBuilder {

    final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public <T> T getObjectMapping(List<ExcelField> excelFields, Class<T> clazz) {
        T t = processField(clazz);
        excelFields.forEach(ex -> addFieldData(ex, t));
        return t;
    }

    private <T> T processField(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return t;
    }

    private <T> void addFieldData(ExcelField evc, T t) {
        Class<? extends Object> classz = t.getClass();
        for (Field field : classz.getDeclaredFields()) {
            field.setAccessible(true);
            if (evc.getPojoAttribute().equalsIgnoreCase(field.getName())) {
                try {
                    addField(evc, t, field);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private <T> void addField(ExcelField evc, T t, Field field) throws IllegalAccessException {
        if (FieldType.STRING.getValue().equalsIgnoreCase(evc.getExcelColType())) {
            field.set(t, evc.getExcelValue());
        } else if (FieldType.DOUBLE.getValue().equalsIgnoreCase(evc.getExcelColType())) {
            field.set(t, Double.valueOf(evc.getExcelValue()));
        } else if (FieldType.INTEGER.getValue().equalsIgnoreCase(evc.getExcelColType())) {
            field.set(t, Double.valueOf(evc.getExcelValue()).intValue());
        } else if (FieldType.DATE.getValue().equalsIgnoreCase(evc.getExcelColType())) {
            field.set(t, LocalDate.parse(evc.getExcelValue(), dtf));
        }
    }
}
