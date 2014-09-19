package com.web;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by R500 on 17.7.2014 г..
 */
// CrudDao class is used to access the data from the database
public class CrudDao {

//    getApprovedVacations method is used to get all vacations that have vacationStatus = 2(approved)
//    and it returns List<Vacation> with the needed data for the FullCalendar in vacationCalendar.html
//    this method is used in VacationCalendarController
    public static List<Vacation> getApprovedVacations(int vacationType){
        Connection conn = DBConnection.getConnection();
        List<Vacation> vacations = new ArrayList<Vacation>();
        PreparedStatement pstFirst = null;
        PreparedStatement pstSecond = null;
        ResultSet rsFirst = null;
        ResultSet rsSecond = null;
        try {
            pstFirst = conn.prepareStatement("select beginDate, endDate, employee_id from vacations where vacationStatus = 2 and vacationType = ?");
            pstFirst.setInt(1, vacationType);
            rsFirst = pstFirst.executeQuery();

            while (rsFirst.next()){
                Vacation vacation = new Vacation();
                pstSecond = conn.prepareStatement("select employeeName from employees where employee_id = ?");
                pstSecond.setInt(1, rsFirst.getInt("employee_id"));
                rsSecond = pstSecond.executeQuery();
                if (rsSecond.next()) {
                    vacation.setEmployeeName(rsSecond.getString("employeeName"));
                }
                vacation.setBeginDate(rsFirst.getString("beginDate"));
                vacation.setEndDate(rsFirst.getString("endDate"));
                vacations.add(vacation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pstFirst != null)
                    pstFirst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pstSecond != null)
                    pstSecond.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rsFirst != null)
                    rsFirst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rsSecond != null)
                    rsSecond.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pstFirst = null;
            pstSecond = null;
            rsFirst = null;
            rsSecond = null;
        }
        return vacations;
    }

//    getSelectedApprovedVacations is used to get the approved vacations of only one user whose name is passed to the method
//    method is used in VacationCalendarController for the FullCalendar in vacationCalendar.html
    public static List<Vacation> getSelectedApprovedVacations(String name, int vacationType){
        Connection conn = DBConnection.getConnection();
        List<Vacation> vacations = new ArrayList<Vacation>();
        PreparedStatement pstFirst = null;
        PreparedStatement pstSecond = null;
        ResultSet rsFirst = null;
        ResultSet rsSecond = null;
        try {
            pstFirst = conn.prepareStatement("select beginDate, endDate, employee_id from vacations where vacationStatus = 2 and vacationType = ?");
            pstFirst.setInt(1, vacationType);
            rsFirst = pstFirst.executeQuery();

            while (rsFirst.next()){
                Vacation vacation = new Vacation();
                pstSecond = conn.prepareStatement("select employeeName from employees where employee_id = ?");
                pstSecond.setInt(1, rsFirst.getInt("employee_id"));
                rsSecond = pstSecond.executeQuery();
                rsSecond.next();
                if (rsSecond.getString("employeeName").equals(name)) {
                    vacation.setEmployeeName(rsSecond.getString("employeeName"));
                    vacation.setBeginDate(rsFirst.getString("beginDate"));
                    vacation.setEndDate(rsFirst.getString("endDate"));
                    vacations.add(vacation);
                } else { continue;}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pstFirst != null)
                    pstFirst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pstSecond != null)
                    pstSecond.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rsFirst != null)
                    rsFirst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rsSecond != null)
                    rsSecond.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pstFirst = null;
            pstSecond = null;
            rsFirst = null;
            rsSecond = null;
        }
        return vacations;
    }


//    getAllVacations is not used anywhere but it can be used if we want the admin to be able of seeing all vacations in the jTable from requestManager.html
    public static List<Vacation> getAllVacations(int jtStartIndex, int jtPageSize, String jtSorting){
        Connection conn = DBConnection.getConnection();
        List<Vacation> vacations = new ArrayList<Vacation>();
        String startIndex = Integer.toString(jtStartIndex);
        String pageSize = Integer.toString(jtPageSize);
        Statement st = null;
        PreparedStatement pst = null;
        ResultSet rsFirst = null;
        ResultSet rsSecond = null;
        try {
            st = conn.createStatement();
            rsFirst = st.executeQuery("select vacation_id, beginDate, endDate, vacationType, vacationStatus, employee_id, requestText, responseText from vacations " +
                                      "order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);

            while (rsFirst.next()){
                Vacation vacation = new Vacation();
                pst = conn.prepareStatement("select employeeName from employees where employee_id = ?");
                pst.setInt(1, rsFirst.getInt("employee_id"));
                rsSecond = pst.executeQuery();
                if (rsSecond.next()) {
                    vacation.setEmployeeName(rsSecond.getString("employeeName"));
                }
//                vacation.setEmployee_id();
                vacation.setVacationID(rsFirst.getInt("vacation_id"));
                vacation.setBeginDate(rsFirst.getString("beginDate"));
                vacation.setEndDate(rsFirst.getString("endDate"));
                vacation.setVacationType(rsFirst.getInt("vacationType"));
                vacation.setRequestText(rsFirst.getString("requestText"));
                vacation.setResponseText(rsFirst.getString("responseText"));
                vacation.setVacationStatus(rsFirst.getInt("vacationStatus"));
                vacations.add(vacation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rsFirst != null)
                    rsFirst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rsSecond != null)
                    rsSecond.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            pst = null;
            rsFirst = null;
            rsSecond = null;
        }
        return vacations;
    }

//    getVacationRequests is used to get only vacation requests that are manged by the current user
//    his employee_ID is used to select only vacation requests which are from users that he manages
//    this method is used in the RequestManagerController for the jTable from requestManager.html
    public static List<Vacation> getVacationRequests(int employee_id, int jtStartIndex, int jtPageSize, String jtSorting){
        Connection conn = DBConnection.getConnection();
        List<Vacation> vacations = new ArrayList<Vacation>();
        String startIndex = Integer.toString(jtStartIndex);
        String pageSize = Integer.toString(jtPageSize);
        PreparedStatement pstFirst = null;
        PreparedStatement pstSecond = null;
        ResultSet rsFirst = null;
        ResultSet rsSecond = null;
        try {
            pstFirst = conn.prepareStatement("select * from vacations where myManager = ?" +
                                             " order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);
            pstFirst.setInt(1, employee_id);
            rsFirst = pstFirst.executeQuery();

            while (rsFirst.next()){
                Vacation vacation = new Vacation();

                pstSecond = conn.prepareStatement("select employeeName from employees where employee_id = ?");
                pstSecond.setInt(1, rsFirst.getInt("employee_id"));
                rsSecond = pstSecond.executeQuery();
                if (rsSecond.next())
                    vacation.setEmployeeName(rsSecond.getString("employeeName"));

                vacation.setVacationID(rsFirst.getInt("vacation_id"));
                vacation.setBeginDate(rsFirst.getString("beginDate"));
                vacation.setEndDate(rsFirst.getString("endDate"));
                vacation.setVacationType(rsFirst.getInt("vacationType"));
                vacation.setRequestText(rsFirst.getString("requestText"));
                vacation.setResponseText(rsFirst.getString("responseText"));
                vacation.setVacationStatus(rsFirst.getInt("vacationStatus"));
                vacations.add(vacation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pstFirst != null)
                    pstFirst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pstSecond != null)
                    pstSecond.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rsFirst != null)
                    rsFirst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rsSecond != null)
                    rsSecond.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pstFirst = null;
            pstSecond = null;
            rsFirst = null;
            rsSecond = null;
        }
        return vacations;
    }

//    getRequestNotifications is used to get the vacation requests that are waiting for approval and are managed by the current user
//    the method is used in RequestNotificationController for the pop-up message after login that says how many new requests are sent to the current user
    public static List<String> getRequestNotifications(int managerID){
        Connection conn = DBConnection.getConnection();
        List<String> notifications = new ArrayList<String>();
        PreparedStatement pstFirst = null;
        PreparedStatement pstSecond = null;
        ResultSet rsFirst = null;
        ResultSet rsSecond = null;
        try {
            pstFirst = conn.prepareStatement("select employee_id from vacations where myManager = ? and vacationStatus = 1");
            pstFirst.setInt(1, managerID);
            rsFirst = pstFirst.executeQuery();

            while (rsFirst.next()){
                pstSecond = conn.prepareStatement("select employeeName from employees where employee_id = ?");
                pstSecond.setInt(1, rsFirst.getInt("employee_id"));
                rsSecond = pstSecond.executeQuery();
                if (rsSecond.next()){
                    notifications.add(rsSecond.getString("employeeName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pstFirst != null)
                    pstFirst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pstSecond != null)
                    pstSecond.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rsFirst != null)
                    rsFirst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rsSecond != null)
                    rsSecond.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pstFirst = null;
            pstSecond = null;
            rsFirst = null;
            rsSecond = null;
        }
        return notifications;
    }

//    getMyVacations is used to get only the vacation requests of the current user
//    the method is used in MyRequestsController fot the jTable in myRequsests.html
    public static List<Vacation> getMyVacations(int employeeID, int jtStartIndex, int jtPageSize, String jtSorting){
        Connection conn = DBConnection.getConnection();
        List<Vacation> vacations = new ArrayList<Vacation>();
        String startIndex = Integer.toString(jtStartIndex);
        String pageSize = Integer.toString(jtPageSize);
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement("select vacation_id, beginDate, endDate, vacationType, requestText, responseText, vacationStatus from vacations where employee_id = ? " +
                                                      "order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);
            preparedStatement.setInt(1, employeeID);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Vacation vacation = new Vacation();
                vacation.setVacationID(rs.getInt("vacation_id"));
                vacation.setBeginDate(rs.getString("beginDate"));
                vacation.setEndDate(rs.getString("endDate"));
                vacation.setVacationType(rs.getInt("vacationType"));
                vacation.setRequestText(rs.getString("requestText"));
                vacation.setResponseText(rs.getString("responseText"));
                vacation.setVacationStatus(rs.getInt("vacationStatus"));
                vacations.add(vacation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (preparedStatement != null)
                    preparedStatement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            preparedStatement = null;
            rs = null;
        }
        return vacations;
    }

//    getVacationStatus provides the vacation status via passed vacationID
//    the method is used in MyRequestsController and RequestManagerController
    public static int getVacationStatus(int vacationID){
        int vacationStatus = 0;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select vacationStatus from vacations where vacation_id = ?");
            pst.setInt(1, vacationID);
            rs = pst.executeQuery();
            rs.next();
            vacationStatus = rs.getInt("vacationStatus");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pst = null;
            rs = null;
        }
        return vacationStatus;
    }

//    getVacationType provides the vacation type via passed vacationID
//    the method is used in MyRequestsController and RequestManagerController
    public static int getVacationType(int vacationID){
        int vacationType = 0;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select vacationType from vacations where vacation_id = ?");
            pst.setInt(1, vacationID);
            rs = pst.executeQuery();
            rs.next();
            vacationType = rs.getInt("vacationType");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pst = null;
            rs = null;
        }
        return vacationType;
    }

//    getVacationBeginDate provides the vacation begin date via passed vacationID
//    the method is used in RequestManagerController
    public static String getVacationBeginDate(int vacationID){
        String beginDate = "";
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select beginDate from vacations where vacation_id = ?");
            pst.setInt(1, vacationID);
            rs = pst.executeQuery();
            rs.next();
            beginDate = rs.getString("beginDate");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pst = null;
            rs = null;
        }
        return beginDate;
    }

//    getVacationEndDate provides the vacation end date via passed vacationID
//    the method is used in RequestManagerController
    public static String getVacationEndDate(int vacationID){
        String endDate = "";
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select endDate from vacations where vacation_id = ?");
            pst.setInt(1, vacationID);
            rs = pst.executeQuery();
            rs.next();
            endDate = rs.getString("endDate");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pst = null;
            rs = null;
        }
        return endDate;
    }

//    getVacationResponseText provides the vacation response text via passed vacationID
//    the method is used in RequestManagerController
    public static String getVacationResponseText(int vacationID){
        String responseText = "";
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select responseText from vacations where vacation_id = ?");
            pst.setInt(1, vacationID);
            rs = pst.executeQuery();
            rs.next();
            responseText = rs.getString("responseText");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pst = null;
            rs = null;
        }
        return responseText;
    }

//    getVacationDaysLeft provides employee's vacation days left via passed employeeID
//    the method is used in MyRequestsController
    public static int getVacationDaysLeft(int employeeID){
        int vacationDaysLeft = 0;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select vacationDaysLeft from employees where employee_id = ?");
            pst.setInt(1, employeeID);
            rs = pst.executeQuery();
            rs.next();
            vacationDaysLeft = rs.getInt("vacationDaysLeft");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pst = null;
            rs = null;
        }
        return vacationDaysLeft;
    }

//    getRequestCount counts the vacations which are managed by the current user
//    the method is used in RequestManagerController for jTable paging
    public static int getRequestCount(int employee_id){
        int count=0;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select COUNT(*) from vacations where myManager = ?");
            pst.setInt(1, employee_id);
            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt("C1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pst = null;
            rs = null;
        }
        return count;
    }

//    getVacationCount is not used anywhere but it can be used if we want the admin to be able of seeing all vacations in the jTable from requestManager.html
//    then we will need to use this method to get the count of all vacations for the jTable paging
    public static int getVacationCount(){
        int count=0;
        Connection conn = DBConnection.getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn. createStatement();
            rs = st.executeQuery("select COUNT(*) from vacations");
            rs.next();
            count = rs.getInt("C1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return count;
    }

//    getVacationCount counts the vacations of the current user
//    the method is used in MyRequestsController for jTable paging
    public static int getVacationCount(int employeeID){
        int count=0;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select COUNT(*) from vacations where employee_id = ?");
            pst.setInt(1, employeeID);
            rs = pst.executeQuery();
            rs.next();
            count = rs.getInt("C1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            pst = null;
            rs = null;
        }
        return count;
    }

//    getAllEmployees gets all employees for jTable from Control panel
//    the method is used in EmployeeTableController
    public static List<Employee> getAllEmployees(int jtStartIndex, int jtPageSize, String jtSorting){
        Connection conn = DBConnection.getConnection();
        List<Employee> employees = new ArrayList<Employee>();
        String startIndex = Integer.toString(jtStartIndex);
        String pageSize = Integer.toString(jtPageSize);

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select fName, surname, lName, employee_id, employeeName, email, accessLevel, myManager, vacationDaysLeft from employees " +
                                 "order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);
            while (rs.next()){
                Employee employee = new Employee();

                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setfName(rs.getString("fName"));
                employee.setSurname(rs.getString("surname"));
                employee.setlName(rs.getString("lName"));
                employee.setEmployeeName(rs.getString("employeeName"));
                employee.setEmail(rs.getString("email"));
//                employee.setPassword(rs.getString("password"));
                employee.setAccessLevel(rs.getInt("accessLevel"));
                employee.setMyManager(rs.getInt("myManager"));
                employee.setVacationDaysLeft(rs.getInt("vacationDaysLeft"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return employees;
    }

//    getEmployeesVacationDaysLeft gets all employees' vacation days left
//    the method is used in UpdateVacationDaysLeftServlet
    public static List<Employee> getEmployeesVacationDaysLeft(){
        Connection conn = DBConnection.getConnection();
        List<Employee> employees = new ArrayList<Employee>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select employee_id, vacationDaysLeft from employees ");
            while (rs.next()){
                Employee employee = new Employee();

                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setVacationDaysLeft(rs.getInt("vacationDaysLeft"));

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return employees;
    }

//    getEmployeesName gets all employees' names
//    the method is used in EmployeeListServlet
    public static List<Employee> getEmployeesName(){
        Connection conn = DBConnection.getConnection();
        List<Employee> employees = new ArrayList<Employee>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select employee_id, employeeName from employees ");
            while (rs.next()){
                Employee employee = new Employee();

                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setEmployeeName(rs.getString("employeeName"));

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return employees;
    }

//    getManagers gets all employees which are managers
//    the method is used in ManagerListServlet
    public static List<Employee> getManagers(){
        Connection conn = DBConnection.getConnection();
        List<Employee> employees = new ArrayList<Employee>();

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select employee_id, employeeName from employees where accessLevel = 1 or accessLevel = 2");
            while (rs.next()){
                Employee employee = new Employee();
                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setEmployeeName(rs.getString("employeeName"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return employees;
    }

//    getEmployeeCount counts all employees
//    the method is used in EmployeeTableController for jTable paging
    public static int getEmployeeCount(){
        int count=0;
        Connection conn = DBConnection.getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn. createStatement();
            rs = st.executeQuery("select COUNT(*) from employees");
            rs.next();
            count = rs.getInt("C1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return count;
    }

//    isEmailTaken checks if the given email exists in the database
//    the method is used in SignUpServlet
    public static boolean isEmailTaken(String email){
        boolean isEmailTaken = false;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement("select * from employees where email = ?");
            pst.setString(1, email);
            rs = pst.executeQuery();
            if (rs.next()){
                isEmailTaken = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return isEmailTaken;
    }

//    getInfoForDocument gets the needed information for the PDF document which is generated and sent via email
//    this method is used in RequestManagerController
    public static Map getInfoForDocument(int vacationID){
        Map info = new HashMap();
        int employeeID = -1;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstFirst = null;
        PreparedStatement pstSecond = null;
        PreparedStatement pstThird = null;
        ResultSet rs = null;

        try {
            pstFirst = conn.prepareStatement("select employee_id from vacations where vacation_id = ?");
            pstFirst.setInt(1, vacationID);
            rs = pstFirst.executeQuery();
            rs.next();
            employeeID = rs.getInt("employee_id");

            pstSecond = conn.prepareStatement("select employeeName, vacationDaysLeft from employees where employee_id = ?");
            pstSecond.setInt(1, employeeID);
            rs = pstSecond.executeQuery();
            rs.next();
            info.put("employeeName", rs.getString("employeeName"));
            info.put("vacationDaysLeft", rs.getInt("vacationDaysLeft"));

            pstThird = conn.prepareStatement("select beginDate, endDate, vacationType from vacations where vacation_id = ?");
            pstThird.setInt(1, vacationID);
            rs = pstThird.executeQuery();
            rs.next();
            info.put("beginDate", rs.getString("beginDate"));
            info.put("endDate", rs.getString("endDate"));
            info.put("vacationType", rs.getString("vacationType"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pstFirst != null)
                    pstFirst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pstSecond != null)
                    pstSecond.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pstThird != null)
                    pstThird.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return info;
    }

//    getEmployeeName provides employee's name via given employeeID
//    the method is used in MyRequestsController
    public static String getEmployeeName(int employeeID){
        String names = "";
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement("select employeeName from employees where employee_id = ?");
            pst.setInt(1, employeeID);
            rs = pst.executeQuery();
            rs.next();
            names = rs.getString("employeeName");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return names;
    }

//    getManagerID provides the ID of the manager of the current user
//    the method is used in MyRequestsController
    public static int getManagerID(int employeeID){
        int myManager = 0;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement("select myManager from employees where employee_id = ?");
            pst.setInt(1, employeeID);
            rs = pst.executeQuery();
            rs.next();
            myManager = rs.getInt("myManager");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return myManager;
    }

//    getPassword provides the password via given employeeID
//   the method is used in ChangePasswordServlet and UpdateVacationDaysLeftServlet
    public static String getPassword(int employeeID){
        String password = "";
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement("select password from employees where employee_id = ?");
            pst.setInt(1, employeeID);
            rs = pst.executeQuery();
            rs.next();
            password = rs.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return password;
    }

//    getAccountStatus provides account status via given employeeID
//    but the method is never used
    public static int getAccountStatus(int employeeID){
        int accountStatus = 0;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement("select accountStatus from employees where employee_id = ?");
            pst.setInt(1, employeeID);
            rs = pst.executeQuery();
            rs.next();
            accountStatus = rs.getInt("accountStatus");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return accountStatus;
    }

//    addVacation creates new record in vacations table
//    this method is used in MyRequestsController
    public static int addVacation(Vacation vacation){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int key = -1;
        try {
            pst = conn.prepareStatement("insert into vacations (beginDate, endDate, employee_id, vacationType, requestText, myManager ,vacationStatus)" +
                                        " values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,vacation.getBeginDate());
            pst.setString(2, vacation.getEndDate());
            pst.setInt(3, vacation.getEmployeeID());
            pst.setInt(4, vacation.getVacationType());
            pst.setString(5, vacation.getRequestText());
            pst.setInt(6, vacation.getMyManager());
            pst.setInt(7, vacation.getVacationStatus());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next())
                key = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return key;
    }

//    addEmployee creates new record in employees table
//    this method is used in EmployeeTableController and SignUpServlet
    public static int addEmployee(Employee employee){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int key = -1;
        try {
            pst = conn.prepareStatement("insert into Employees (fName, surname, lName, employeeName, email, password, accessLevel, accountStatus, myManager, vacationDaysLeft, forgottenPasswordCode)" +
                                        " values (?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, employee.getfName());
            pst.setString(2, employee.getSurname());
            pst.setString(3, employee.getlName());
            pst.setString(4, employee.getEmployeeName());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getPassword());
            pst.setInt(7, employee.getAccessLevel());
            pst.setInt(8, employee.getAccountStatus());
            pst.setInt(9, employee.getMyManager());
            pst.setInt(10, employee.getVacationDaysLeft());
            pst.setString(11, "");
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next())
                key = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return key;
    }

//    updateVacationDates updates the record's begin date, end date and the request text in vacations table
//    this method is used in MyRequestsController
    public static void updateVacationDates(Vacation vacation){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update vacations set beginDate = ?, endDate = ?, requestText = ? where vacation_id = ?");
            pst.setString(1, vacation.getBeginDate());
            pst.setString(2, vacation.getEndDate());
            pst.setString(3, vacation.getRequestText());
            pst.setInt(4, vacation.getVacationID());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateVacationResponseText updates the record's response text in vacations table
//    this method is used in RequestManagerController
    public static void updateVacationResponseText(String responseText, int vacationID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update vacations set responseText = ? where vacation_id = ?");
            pst.setString(1, responseText);
            pst.setInt(2, vacationID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateVacationStatus updates the record's response text and vacation status in vacations table
//    this method is used in RequestManagerController
    public static void updateVacationStatus(Vacation vacation){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update vacations set responseText = ?, vacationStatus = ? where vacation_id = ?");
            pst.setString(1, vacation.getResponseText());
            pst.setInt(2, vacation.getVacationStatus());
            pst.setInt(3, vacation.getVacationID());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateVacationManager updates the manager of the vacation
//    this method is used in EmployeeTableController
    public static void updateVacationManager(int employeeID, int myManager){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update vacations set myManager = ? where employee_id = ?");
            pst.setInt(1, myManager);
            pst.setInt(2, employeeID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateEmployee updates a record in employees table
//    this method is used in EmployeeTableController
    public static void updateEmployee(Employee employee){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update employees set fName = ?, surname = ?, lName = ?, employeeName = ?, email = ?, accessLevel = ?, myManager = ?," +
                                        " vacationDaysLeft = ? where employee_id = ?");
            pst.setString(1, employee.getfName());
            pst.setString(2, employee.getSurname());
            pst.setString(3, employee.getlName());
            pst.setString(4, employee.getEmployeeName());
            pst.setString(5, employee.getEmail());
            pst.setInt(6, employee.getAccessLevel());
            pst.setInt(7, employee.getMyManager());
            pst.setInt(8, employee.getVacationDaysLeft());
            pst.setInt(9, employee.getEmployee_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateEmployeePassword updates employee's password
//    this method is used in ChangePasswordServlet and ResetForgottenPassword
    public static void updateEmployeePassword(String newPass, int employeeID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update employees set password = ? where employee_id = ?");
            pst.setString(1, newPass);
            pst.setInt(2, employeeID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateForgottenPasswordCodeAndLinkExpiryDate sets a code and the link expiry date of an employee
//    this method is used in ForgottenPassword and ResetForgottenPassword
    public static void updateForgottenPasswordCodeAndLinkExpiryDate(String code, String expiryDate, int employeeID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update employees set forgottenPasswordCode = ?, linkExpiryDate = ? where employee_id = ?");
            pst.setString(1, code);
            pst.setString(2, expiryDate);
            pst.setInt(3, employeeID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateVacationDaysLeft updates vacation days left of an employee by subtracting the given days from the vacation days left in the database
//    the method is used in RequestManagerController
    public static void updateVacationDaysLeft(int days, int employeeID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstFirst = null;
        PreparedStatement pstSecond = null;
        ResultSet rs = null;
        try {
            pstFirst = conn.prepareStatement("select vacationDaysLeft from employees where employee_id = ?");
            pstFirst.setInt(1, employeeID);
            rs = pstFirst.executeQuery();
            rs.next();

            pstSecond = conn.prepareStatement("update employees set vacationDaysLeft = ? where employee_id = ?");
            pstSecond.setInt(1, rs.getInt("vacationDaysLeft") - days);
            pstSecond.setInt(2, employeeID);
            pstSecond.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pstFirst != null)
                    pstFirst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pstSecond != null)
                    pstSecond.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (rs != null)
                    rs.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateVacationDaysLeft updates vacation days left of an employee
//    the method is used in UpdateVacationDaysLeftServlet
    public static void updateVacationDaysLeftEveryYear(int days, int employeeID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update employees set vacationDaysLeft = ? where employee_id = ?");
            pst.setInt(1, days);
            pst.setInt(2, employeeID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    updateEmployeeAccountStatus updates employee's account status
//    the method is used in ChangePasswordServlet and ResetForgottenPassword
    public static void updateEmployeeAccountStatus(int employeeID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update employees set accountStatus = 1 where employee_id = ?");
            pst.setInt(1, employeeID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    deleteVacation deletes record in vacations table
//    the method is used in MyRequestsController and RequestManagerController
    public static void deleteVacation(int vacationID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("delete from vacations where vacation_id = ?");
            pst.setInt(1, vacationID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    cascadeDeleteEmployee first deletes all the vacations of the employee that we want to delete
//    after that it deletes the employee record in employees table
//    the method is used in EmployeeTableController
    public static void cascadeDeleteEmployee(int employeeID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstFirst = null;
        PreparedStatement pstSecond = null;
        try {
            pstFirst = conn.prepareStatement("delete from vacations where employee_id = ?");
            pstFirst.setInt(1, employeeID);
            pstFirst.executeUpdate();

            pstSecond = conn.prepareStatement("delete from employees where employee_id = ?");
            pstSecond.setInt(1, employeeID);
            pstSecond.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pstFirst != null)
                    pstFirst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try{
                if (pstSecond != null)
                    pstSecond.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

//    methods for accessing the information about holidays and working saturdays tables
//    these methods are used in HolidaysListServlet, HolidaysTableController, Holidays, WorkingSaturdaysListServlet, WorkingSaturdaysController and WorkingSaturdays
    public static List<Holiday> getAllHolidays(){
        Connection conn = DBConnection.getConnection();
        List<Holiday> holidays = new ArrayList<Holiday>();

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select day, year, month, text from holidays");
            while (rs.next()){
                Holiday holiday = new Holiday();

//                holiday.setHoliday_id(rs.getInt("holiday_id"));
                holiday.setYear(rs.getInt("year"));
                holiday.setMonth(rs.getInt("month"));
                holiday.setDay(rs.getInt("day"));
                holiday.setText(rs.getString("text"));

                holidays.add(holiday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return holidays;
    }

    public static List<Holiday> getHolidaysDates(){
        Connection conn = DBConnection.getConnection();
        List<Holiday> holidays = new ArrayList<Holiday>();

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select day, year, month from holidays");
            while (rs.next()){
                Holiday holiday = new Holiday();

                holiday.setYear(rs.getInt("year"));
                holiday.setMonth(rs.getInt("month"));
                holiday.setDay(rs.getInt("day"));

                holidays.add(holiday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return holidays;
    }

    public static List<Holiday> getAllHolidays(int jtStartIndex, int jtPageSize, String jtSorting){
        Connection conn = DBConnection.getConnection();
        List<Holiday> holidays = new ArrayList<Holiday>();
        String startIndex = Integer.toString(jtStartIndex);
        String pageSize = Integer.toString(jtPageSize);

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from holidays "+ "order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);
            while (rs.next()){
                Holiday holiday = new Holiday();

                holiday.setHoliday_id(rs.getInt("holiday_id"));
                holiday.setYear(rs.getInt("year"));
                holiday.setMonth(rs.getInt("month"));
                holiday.setDay(rs.getInt("day"));
                holiday.setText(rs.getString("text"));

                holidays.add(holiday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return holidays;
    }

    public static int getHolidaysCount(){
        int count=0;
        Connection conn = DBConnection.getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select COUNT(*) from holidays");
            rs.next();
            count = rs.getInt("C1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return count;
    }

    public static int addHoliday(Holiday holiday){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int key = -1;
        try {
            pst = conn.prepareStatement("insert into Holidays (day, month, year, text)" +
                    " values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, holiday.getDay());
            pst.setInt(2, holiday.getMonth());
            pst.setInt(3, holiday.getYear());
            pst.setString(4, holiday.getText());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next())
                key = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return key;
    }

    public static void updateHoliday(Holiday holiday){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update holidays set day = ?, month = ?, year = ?, text = ? where holiday_id = ?");
            pst.setInt(1, holiday.getDay());
            pst.setInt(2, holiday.getMonth());
            pst.setInt(3, holiday.getYear());
            pst.setString(4, holiday.getText());
            pst.setInt(5, holiday.getHoliday_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

    public static void deleteHoliday(int holidayID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("delete from Holidays where holiday_id = ?");
            pst.setInt(1, holidayID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

    public static List<WorkingSaturday> getWorkingSaturdaysDates(){
        Connection conn = DBConnection.getConnection();
        List<WorkingSaturday> workingSaturdays = new ArrayList<WorkingSaturday>();

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select day, month, year from workingSaturdays");
            while (rs.next()){
                WorkingSaturday workingSaturday = new WorkingSaturday();

                workingSaturday.setYear(rs.getInt("year"));
                workingSaturday.setMonth(rs.getInt("month"));
                workingSaturday.setDay(rs.getInt("day"));

                workingSaturdays.add(workingSaturday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return workingSaturdays;
    }

    public static List<WorkingSaturday> getAllWorkingSaturdays(){
        Connection conn = DBConnection.getConnection();
        List<WorkingSaturday> workingSaturdays = new ArrayList<WorkingSaturday>();

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select day, month, year, text from workingSaturdays");
            while (rs.next()){
                WorkingSaturday workingSaturday = new WorkingSaturday();

//                workingSaturday.setWorkingSaturday_id(rs.getInt("workingSaturday_id"));
                workingSaturday.setYear(rs.getInt("year"));
                workingSaturday.setMonth(rs.getInt("month"));
                workingSaturday.setDay(rs.getInt("day"));
                workingSaturday.setText(rs.getString("text"));

                workingSaturdays.add(workingSaturday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return workingSaturdays;
    }

    public static List<WorkingSaturday> getAllWorkingSaturdays(int jtStartIndex, int jtPageSize, String jtSorting){
        Connection conn = DBConnection.getConnection();
        List<WorkingSaturday> workingSaturdays = new ArrayList<WorkingSaturday>();
        String startIndex = Integer.toString(jtStartIndex);
        String pageSize = Integer.toString(jtPageSize);

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from workingSaturdays "+ "order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);
            while (rs.next()){
                WorkingSaturday workingSaturday = new WorkingSaturday();

                workingSaturday.setWorkingSaturday_id(rs.getInt("workingSaturday_id"));
                workingSaturday.setYear(rs.getInt("year"));
                workingSaturday.setMonth(rs.getInt("month"));
                workingSaturday.setDay(rs.getInt("day"));
                workingSaturday.setText(rs.getString("text"));

                workingSaturdays.add(workingSaturday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return workingSaturdays;
    }

    public static int getWorkingSaturdaysCount(){
        int count=0;
        Connection conn = DBConnection.getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select COUNT(*) from workingSaturdays");
            rs.next();
            count = rs.getInt("C1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (st != null)
                    st.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
            conn = null;
            st = null;
            rs = null;
        }
        return count;
    }

    public static int addWorkingSaturday(WorkingSaturday workingSaturday){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int key = -1;
        try {
            pst = conn.prepareStatement("insert into workingSaturdays (day, month, year, text)" +
                    " values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, workingSaturday.getDay());
            pst.setInt(2, workingSaturday.getMonth());
            pst.setInt(3, workingSaturday.getYear());
            pst.setString(4, workingSaturday.getText());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next())
                key = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
        return key;
    }

    public static void updateWorkingSaturday(WorkingSaturday workingSaturday){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update workingSaturdays set day = ?, month = ?, year = ?, text = ? where workingSaturday_id = ?");
            pst.setInt(1, workingSaturday.getDay());
            pst.setInt(2, workingSaturday.getMonth());
            pst.setInt(3, workingSaturday.getYear());
            pst.setString(4, workingSaturday.getText());
            pst.setInt(5, workingSaturday.getWorkingSaturday_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }

    public static void deleteWorkingSaturday(int workingSaturdayID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("delete from workingSaturdays where workingSaturday_id = ?");
            pst.setInt(1, workingSaturdayID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (pst != null)
                    pst.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }
    }


}



