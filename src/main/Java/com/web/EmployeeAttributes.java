package com.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by R500 on 16.7.2014 г..
 */
public class EmployeeAttributes {
    public static int getEmployeeAccessLevel(String email, String pass){
        int accessLevel = 2;

        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("select access_level from employees where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            accessLevel = resultSet.getInt("access_level");
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

    public static int getEmployeeID(String email, String pass){
        int employeeID = -1;

        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("select employee_ID from employees where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            employeeID = resultSet.getInt("employee_ID");
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

    public static int getAccountStatus(String email, String pass){
        int accountStatus = 0;

        Connection conn = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("select accountStatus from employees where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
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
}
