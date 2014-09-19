package com.web;

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

//    private static Days holidays = new Holidays();
//    private static Days workingSaturdays = new WorkingSaturdays();

//    getBusinessDaysCount counts the working days between d1 and d2 by excluding the holidays and working saturdays
    public static int getBusinessDaysCount(Date d1, Date d2, Days holidays, Days workingSaturdays) {

        int businessDaysCount = 0;

//            Initial dates should both be working days
        if (!isWorkingDay(d1, holidays, workingSaturdays) || !isWorkingDay(d2, holidays, workingSaturdays)) {
//            businessDaysCount = -1 is a key which indicates that the dates are not both working days
            businessDaysCount = -1;
            return businessDaysCount;
        }

//        check if the begin date and the end date are the same day
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
            if (isWorkingDay(calendar.getTime(), holidays, workingSaturdays)) {
                businessDaysCount++;
            }
            key = 1;
        } while (calendar.getTime().before(max));

        return businessDaysCount;
    }

//    excludeDaysOff returns data for the FullCalendar from vacationCalendar.html
//    the method is used to split the vacation and remove the days off from the view in the calendar
//    for example if the vacation is from Friday to Monday on the calender it will show only Friday and Monday and it will exclude Saturday and Sunday
    public static ArrayList<String[]> excludeDaysOff(String beginDate, String endDate, Days holidays, Days workingSaturdays) {
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
            if (!isWorkingDay(calendar.getTime(), holidays, workingSaturdays)) {
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

//    isWorkingDay returns true if the given date is a working day
//    and returns false if the date is a holiday or part of the weekend excluding the working Saturdays
    public static boolean isWorkingDay(Date date, Days holidays, Days workingSaturdays) {
        calendar.setTime(date);
        if (workingSaturdays.contains(date)){
            return true;
        } else {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                return false;
            } else if (holidays.contains(date)) {
                return false;
            }
            return true;
        }
    }
}
