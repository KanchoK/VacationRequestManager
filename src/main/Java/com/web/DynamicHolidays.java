package com.web;

import java.util.*;

/**
 * Created by R500 on 19.8.2014 г..
 */
public class DynamicHolidays extends Holidays {

    private Map<Integer, Set<Holiday>> dynamicHolidays = new HashMap<Integer, Set<Holiday>>();

    @Override
    public boolean contains(Date date) {
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        if (!dynamicHolidays.containsKey(year)) {
            addYear(year);
        }
        Set<Holiday> dynamicHolidays = this.dynamicHolidays.get(year);
        Holiday dynamicHoliday = new Holiday(date);
        return dynamicHolidays.contains(dynamicHoliday);
    }

    private void addYear(int year) {
        Date easterSunday = getEasterSunday(year);
        Set<Holiday> dynamicHolidays = new HashSet<Holiday>();
        dynamicHolidays.add(new Holiday(getEasterMonday(easterSunday)));
        dynamicHolidays.add(new Holiday(getGoodFriday(easterSunday)));
        this.dynamicHolidays.put(year, dynamicHolidays);
    }

    public static Date getEasterSunday(int year) {

        int initialYear = year;

        if (year < 1900) {
            year += 1900;
        }

// Orthodox eastern date calculator
        int r1 = year % 4;
        int r2 = year % 7;
        int r3 = year % 19;
        int r4 = (19 * r3 + 15) % 30;
        int r5 = (2 * r1 + 4 * r2 + 6 * r4 + 6) % 7;
        int mdays = r5 + r4 + 13;

        int month = -1;
        if (mdays > 39) {
            mdays = mdays - 39;
            month = 4;
        } else if (mdays > 9) {
            mdays = mdays - 9;
            month = 3;
        } else {
            mdays = mdays + 22;
            month = 2;
        }

        calendar.set(initialYear, month, mdays);
        return calendar.getTime();

    }

    public static Date getEasterMonday(Date easterSunday) {
        calendar.setTime(easterSunday);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getGoodFriday(Date easterSunday) {
        calendar.setTime(easterSunday);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        return calendar.getTime();
    }
}
