package com.web;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by R500 on 19.8.2014 г..
 */
public class Holidays extends Days {

    private Set<Day> holidays = new HashSet<Day>();

    public Holidays() {
        List<Holiday> holidayList = CrudDao.getHolidaysDates();
        for (int i = 0; i < holidayList.size(); i++){
            Holiday currentHoliday = holidayList.get(i);
            holidays.add(new Day(currentHoliday.getYear(), currentHoliday.getMonth(), currentHoliday.getDay()));
        }
    }

    @Override
    public boolean contains(Date date) {
        Day holiday = new Day(date);
        return holidays.contains(holiday);
    }

}
