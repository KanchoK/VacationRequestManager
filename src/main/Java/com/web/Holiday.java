package com.web;

/**
 * Created by R500 on 12.9.2014 г..
 */

//Holiday class is used to pass data of the holidays easily
public class Holiday {
    private int holiday_id;
    private int year;
    private int month;
    private int day;
    private String text;

    public int getHoliday_id(){
        return holiday_id;
    }

    public void setHoliday_id(int holiday_id) {
        this.holiday_id = holiday_id;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth(){
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay(){
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
