package com.example.educationdmoseykinapi.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateConverterService {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public String convert(Date date) {
        if (date != null) {
            return dateFormat.format(date);
        } else {
            return null;
        }
    }
}
