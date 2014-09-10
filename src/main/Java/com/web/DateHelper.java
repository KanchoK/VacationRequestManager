package com.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by R500 on 19.8.2014 г..
 */
public class DateHelper {

    private final static Calendar calendar = Calendar.getInstance();

    private final static Holidays staticHolidays = new StaticHolidays();
    private final static Holidays dynamicHolidays = new DynamicHolidays();

    public static int getBusinessDaysCount(Date d1, Date d2) {

        int businessDaysCount = 0;

//            Initial dates should both be working days
        if (!isWorkingDay(d1) || !isWorkingDay(d2)) {
//            businessDaysCount = -1 is a key which indicates that the dates are not both working days
            businessDaysCount = -1;
            return businessDaysCount;
        }

        if (d1.equals(d2)) {
            businessDaysCount = 1;
            return businessDaysCount;
        }

        Date min = d1.before(d2) ? d1 : d2;
        Date max = min.equals(d2) ? d1 : d2;

        int key = 0;

        calendar.setTime(min);
        do {

            if (key != 0){
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            if (isWorkingDay(calendar.getTime())) {
                businessDaysCount++;
            }
            key = 1;
        } while (calendar.getTime().before(max));

        return businessDaysCount;
    }

    public static ArrayList<String[]> excludeDaysOff(String beginDate, String endDate) {
        ArrayList<String[]> data = new ArrayList<String[]>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(beginDate);
            d2 = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String prevDate = beginDate;

        Date min = d1.before(d2) ? d1 : d2;
        Date max = min.equals(d2) ? d1 : d2;

        int key = 0;

        calendar.setTime(min);
        int index = 0;
        do {

            if (key != 0){
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            if (!isWorkingDay(calendar.getTime())) {
                if (!prevDate.equals("")) {
                    String[] vacation = {beginDate, prevDate};
                    data.add(index, vacation);
                    index++;
                    beginDate = "";
                    prevDate = "";
                }
            } else {
                if (beginDate.equals("")){
                    beginDate = sdf.format(calendar.getTime());
                }
                prevDate = sdf.format(calendar.getTime());
                if (calendar.getTime().equals(max)){
                    String[] vacation = {beginDate, endDate};
                    data.add(index, vacation);
                    index++;
                }
            }
            key = 1;
        } while (calendar.getTime().before(max));

        return data;
    }

    public static boolean isWorkingDay(Date date) {
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return false;
        } else if (staticHolidays.contains(date)) {
            return false;
        } else if (dynamicHolidays.contains(date)) {
            return false;
        }
        return true;
    }
}
