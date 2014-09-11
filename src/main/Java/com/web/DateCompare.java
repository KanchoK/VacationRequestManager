package com.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setTime(firstDate);
        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.setTime(secondDate);

        if (firstDate.before(secondDate) || (firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR) &&
                                            firstCalendar.get(Calendar.MONTH) == secondCalendar.get(Calendar.MONTH) &&
                                            firstCalendar.get(Calendar.DAY_OF_MONTH) == secondCalendar.get(Calendar.DAY_OF_MONTH)))
        {
            isDatesValid = true;
        }

        return isDatesValid;
    }
}
