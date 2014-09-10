package com.web;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by R500 on 19.8.2014 г..
 */
public class StaticHolidays extends Holidays{

    private Set<Holiday> staticHolidays = new HashSet<Holiday>();

    public StaticHolidays() {
        staticHolidays.add(new Holiday(1, 1));
        staticHolidays.add(new Holiday(3, 3));
        staticHolidays.add(new Holiday(5, 1));
        staticHolidays.add(new Holiday(5, 6));
        staticHolidays.add(new Holiday(5, 24));
        staticHolidays.add(new Holiday(9, 6));
        staticHolidays.add(new Holiday(9, 22));
        staticHolidays.add(new Holiday(12, 24));
        staticHolidays.add(new Holiday(12, 25));
        staticHolidays.add(new Holiday(12, 26));
    }

    @Override
    public boolean contains(Date date) {
        Holiday staticHoliday = new Holiday(date);
        return staticHolidays.contains(staticHoliday);
    }

}
