package com.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by R500 on 19.8.2014 г..
 */
public class DateHelper {

    private final static Calendar calendar = Calendar.getInstance();
    private final static DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    private final static Holidays staticHolidays = new StaticHolidays();
    private final static Holidays dynamicHolidays = new DynamicHolidays();

    public static int getBusinessDaysCount(Date d1, Date d2) {

        try {
            d1 = formatter.parse(formatter.format(d1));
            d2 = formatter.parse(formatter.format(d2));
        } catch (ParseException e) {
            throw new DateHelperRuntimeException(e);
        }

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

    public static class DateHelperRuntimeException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public DateHelperRuntimeException(String message) {
            super(message);
        }

        public DateHelperRuntimeException(Throwable e) {
            super(e);
        }

    }

}
