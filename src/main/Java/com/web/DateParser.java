package com.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by R500 on 25.8.2014 г..
 */
public class DateParser {
    public static String parseDate(String date){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String parsedDate = "";
        try {
            Date tempDate = dateFormat.parse(date);
            DateFormat parsedFormat = new SimpleDateFormat("yyyy-MM-dd");
            parsedDate = parsedFormat.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parsedDate;
    }
}
