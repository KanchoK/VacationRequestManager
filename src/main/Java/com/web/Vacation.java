package com.web;

/**
 * Created by R500 on 17.7.2014 г..
 */
public class Vacation {
    private int vacationID;
    private int employeeID;
    private String employeeName;
    private String beginDate;
    private String endDate;
    private int vacationType;
    private String requestText;
    private String responseText;
    private int vacationStatus;
//    private int previousVacationStatus;
    private int myManager;

    public int getVacationID(){
        return vacationID;
    }

    public void setVacationID(int vacationID){
        this.vacationID = vacationID;
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

//    public int getPreviousVacationStatus(){
//        return previousVacationStatus;
//    }
//
//    public void setPreviousVacationStatus(int previousVacationStatus){
//        this.previousVacationStatus = previousVacationStatus;
//    }

    public int getVacationStatus(){
        return vacationStatus;
    }

    public void setVacationStatus(int vacationStatus){
        this.vacationStatus = vacationStatus;
    }

    public String getRequestText(){
        return requestText;
    }

    public void setRequestText(String requestText){
        this.requestText = requestText;
    }

    public String getResponseText(){
        return responseText;
    }

    public void setResponseText(String responseText){
        this.responseText = responseText;
    }

    public int getVacationType(){
        return vacationType;
    }

    public void setVacationType(int vacationType){
        this.vacationType = vacationType;
    }

    public int getMyManager(){
        return myManager;
    }

    public void setMyManager(int myManager){
        this.myManager = myManager;
    }
}