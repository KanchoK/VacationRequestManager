package com.web;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R500 on 17.7.2014 г..
 */
public class CrudDao {

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
            rsFirst = st.executeQuery("select holiday_id, beginDate, endDate, holidayStatus, employee_id from holidays " +
                    "                   order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);

            while (rsFirst.next()){
                Vacation vacation = new Vacation();
                pst = conn.prepareStatement("select employeeName from employees where employee_id = ?");
                pst.setInt(1, rsFirst.getInt("employee_id"));
                rsSecond = pst.executeQuery();
                if (rsSecond.next())
                    vacation.setEmployeeName(rsSecond.getString("employeeName"));
//                vacation.setEmployee_id();
                vacation.setHolidayID(rsFirst.getInt("holiday_id"));
                vacation.setBeginDate(rsFirst.getString("beginDate"));
                vacation.setEndDate(rsFirst.getString("endDate"));
                vacation.setHolidayStatus(rsFirst.getString("holidayStatus"));
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

    public static List<Vacation> getMyVacations(int employeeID, int jtStartIndex, int jtPageSize, String jtSorting){
        Connection conn = DBConnection.getConnection();
        List<Vacation> vacations = new ArrayList<Vacation>();
        String startIndex = Integer.toString(jtStartIndex);
        String pageSize = Integer.toString(jtPageSize);
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement("select holiday_id, beginDate, endDate, holidayStatus from holidays where employee_id = ? " +
                    "                                   order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);
            preparedStatement.setInt(1, employeeID);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Vacation vacation = new Vacation();
                vacation.setHolidayID(rs.getInt("holiday_id"));
                vacation.setBeginDate(rs.getString("beginDate"));
                vacation.setEndDate(rs.getString("endDate"));
                vacation.setHolidayStatus(rs.getString("holidayStatus"));
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

    public static int getVacationCount(){
        int count=0;
        Connection conn = DBConnection.getConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn. createStatement();
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

    public static int getVacationCount(int employeeID){
        int count=0;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("select COUNT(*) from holidays where employee_id = ?");
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

    public static List<Employee> getAllEmployees(int jtStartIndex, int jtPageSize, String jtSorting){
        Connection conn = DBConnection.getConnection();
        List<Employee> employees = new ArrayList<Employee>();
        String startIndex = Integer.toString(jtStartIndex);
        String pageSize = Integer.toString(jtPageSize);

        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select fName, surname, lName, employee_id, employeeName, email, access_level from employees order by " + jtSorting + " limit " + pageSize + " offset " + startIndex);
            while (rs.next()){
                Employee employee = new Employee();

                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setfName(rs.getString("fName"));
                employee.setSurname(rs.getString("surname"));
                employee.setlName(rs.getString("lName"));
                employee.setEmployeeName(rs.getString("employeeName"));
                employee.setEmail(rs.getString("email"));
//                employee.setPassword(rs.getString("password"));
                employee.setAccess_level(rs.getInt("access_level"));
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
            DBConnection.closeConnection();
        }
        return isEmailTaken;
    }

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
            DBConnection.closeConnection();
        }
        return names;
    }

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
            DBConnection.closeConnection();
        }
        return password;
    }

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
            DBConnection.closeConnection();
        }
        return accountStatus;
    }

    public static int addVacation(Vacation vacation){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int key = -1;
        try {
            pst = conn.prepareStatement("insert into Holidays (beginDate, endDate, employee_id, holidayStatus) values (?,?,?,1)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, vacation.getBeginDate());
            pst.setString(2, vacation.getEndDate());
            pst.setInt(3, vacation.getEmployeeID());
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

    public static int addEmployee(Employee employee){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int key = -1;
        try {
            pst = conn.prepareStatement("insert into Employees (fName, surname, lName, employeeName, email, password, access_level, accountStatus) values (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, employee.getfName());
            pst.setString(2, employee.getSurname());
            pst.setString(3, employee.getlName());
            pst.setString(4, employee.getEmployeeName());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getPassword());
            pst.setInt(7, employee.getAccess_level());
            pst.setInt(8, employee.getAccountStatus());
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

    public static void updateVacationDates(Vacation vacation){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update holidays set beginDate = ?, endDate = ? where holiday_id = ?");
            pst.setString(1, vacation.getBeginDate());
            pst.setString(2, vacation.getEndDate());
            pst.setInt(3, vacation.getHolidayID());
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

    public static void updateVacationStatus(Vacation vacation){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update holidays set holidayStatus = ? where holiday_id = ?");
            pst.setString(1, vacation.getHolidayStatus());
            pst.setInt(2, vacation.getHolidayID());
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

    public static void updateEmployee(Employee employee){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("update employees set fName = ?, surname = ?, lName = ?, employeeName = ?, email = ?, access_level = ? where employee_id = ?");
            pst.setString(1, employee.getfName());
            pst.setString(2, employee.getSurname());
            pst.setString(3, employee.getlName());
            pst.setString(4, employee.getEmployeeName());
            pst.setString(5, employee.getEmail());
            pst.setInt(6, employee.getAccess_level());
            pst.setInt(7, employee.getEmployee_id());
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

    public static void deleteVacation(int vacationID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("delete from holidays where holiday_id = ?");
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

    public static void cascadeDeleteEmployee(int employeeID){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstFirst = null;
        PreparedStatement pstSecond = null;
        try {
            pstFirst = conn.prepareStatement("delete from holidays where employee_id = ?");
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
}
