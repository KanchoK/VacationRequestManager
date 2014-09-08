package com.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by R500 on 14.8.2014 г..
 */
public class DateCompare {
    public static boolean CompareDates(String firstDate, String secondDate){
        boolean isDatesValid = false;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date parsedFirstDate = sdf.parse(firstDate);
            Date parsedSecondDate = sdf.parse(secondDate);
            if (parsedFirstDate.before(parsedSecondDate) || parsedFirstDate.equals(parsedSecondDate)){
                isDatesValid = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isDatesValid;
    }
    public static boolean CompareJavaDates(Date firstDate, Date secondDate){
        boolean isDatesValid = false;

        if (firstDate.before(secondDate)){
            isDatesValid = true;
        }

        return isDatesValid;
    }
}
