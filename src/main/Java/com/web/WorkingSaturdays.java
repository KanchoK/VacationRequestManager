package com.web;

import java.util.*;

/**
 * Created by R500 on 19.8.2014 г..
 */
public class WorkingSaturdays extends Days {

    private Set<Day> workingSaturdays = new HashSet<Day>();

    public WorkingSaturdays() {
        List<WorkingSaturday> workingSaturdaysList = CrudDao.getWorkingSaturdaysDates();
        for (int i = 0; i < workingSaturdaysList.size(); i++){
            WorkingSaturday currentWorkingSaturday = workingSaturdaysList.get(i);
            workingSaturdays.add(new Day(currentWorkingSaturday.getYear(), currentWorkingSaturday.getMonth(), currentWorkingSaturday.getDay()));
        }
    }

    @Override
    public boolean contains(Date date) {
        Day workingSaturday = new Day(date);
        return workingSaturdays.contains(workingSaturday);
    }
}
