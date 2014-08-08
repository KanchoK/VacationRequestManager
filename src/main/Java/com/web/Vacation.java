package com.web;

/**
 * Created by R500 on 17.7.2014 г..
 */
public class Vacation {
    private int holidayID;
    private int employeeID;
    private String employeeName;

    private String beginDate;
    private String endDate;
    private String holidayStatus;

    public int getHolidayID(){
        return holidayID;
    }

    public void setHolidayID(int holidayID){
        this.holidayID = holidayID;
    }

    public int getEmployeeID(){
        return employeeID;
    }

    public void setEmployeeID(int employeeID){
        this.employeeID = employeeID;
    }

    public String getEmployeeName(){
        return employeeName;
    }

    public void setEmployeeName(String employee){
        this.employeeName = employee;
    }

    public String getBeginDate(){
        return beginDate;
    }

    public void setBeginDate(String beginDate){
        this.beginDate = beginDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public String getHolidayStatus(){
        return holidayStatus;
    }

    public void setHolidayStatus(String holidayStatus){
        this.holidayStatus = holidayStatus;
    }
}