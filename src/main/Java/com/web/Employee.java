package com.web;

/**
 * Created by R500 on 21.7.2014 г..
 */

//Employee class is used to pass data of the employees easily
public class Employee {
    private int employee_id;
    private String fName;
    private String surname;
    private String lName;
    private String employeeName;
    private String email;
    private String password;
    private int accessLevel;
    private int accountStatus;
    private int myManager;
    private int vacationDaysLeft;
    private String forgottenPasswordCode;

    public int getEmployee_id(){
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getMyManager() {
        return myManager;
    }

    public void setMyManager(int myManager) {
        this.myManager = myManager;
    }

    public int getVacationDaysLeft() {
        return vacationDaysLeft;
    }

    public void setVacationDaysLeft(int vacationDaysLeft) {
        this.vacationDaysLeft = vacationDaysLeft;
    }

    public String getForgottenPasswordCode() {
        return forgottenPasswordCode;
    }

    public void setForgottenPasswordCode(String forgottenPasswordCode) {
        this.forgottenPasswordCode = forgottenPasswordCode;
    }
}
