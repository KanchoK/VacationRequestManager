package com.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by R500 on 16.7.2014 г..
 */

//EmployeeAttributes class is used to get the info for an employee from the database via the email they used in the login form
public class EmployeeAttributes {
    public static int getEmployeeAccessLevel(String email){
        int accessLevel = 0;

        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("select accessLevel from employees where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            accessLevel = resultSet.getInt("accessLevel");
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
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }

        return accessLevel;
    }

    public static int getEmployeeID(int vacationID) {
        int employeeID = -1;

        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("select employee_ID from vacations where vacation_id = ?");
            preparedStatement.setInt(1, vacationID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeID = resultSet.getInt("employee_ID");
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
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }

        return employeeID;
    }

    public static int getEmployeeID(String email){
        int employeeID = -1;

        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("select employee_ID from employees where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                employeeID = resultSet.getInt("employee_ID");
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
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }

        return employeeID;
    }

    public static int getAccountStatus(String email){
        int accountStatus = 0;

        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("select accountStatus from employees where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            accountStatus = resultSet.getInt("accountStatus");
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
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }

        return accountStatus;
    }

    public static Map getForgottenPasswordCodeAndLinkExpiryDate(String email){
        Map data = new HashMap();

        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("select forgottenPasswordCode, linkExpiryDate from employees where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            data.put("code", resultSet.getString("forgottenPasswordCode"));
            data.put("expiryDate", resultSet.getString("linkExpiryDate"));
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
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection();
        }

        return data;
    }
}
