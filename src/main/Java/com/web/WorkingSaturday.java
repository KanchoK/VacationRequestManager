package com.web;

/**
 * Created by R500 on 15.9.2014 г..
 */

//WorkingSaturday class is used to pass data of the workingSaturdays easily
public class WorkingSaturday {
    private int workingSaturday_id;
    private int year;
    private int month;
    private int day;
    private String text;

    public int getWorkingSaturday_id(){
        return workingSaturday_id;
    }

    public void setWorkingSaturday_id(int workingSaturday_id) {
        this.workingSaturday_id = workingSaturday_id;
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
