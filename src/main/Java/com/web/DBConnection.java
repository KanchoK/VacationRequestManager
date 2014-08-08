package com.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by R500 on 16.7.2014 г..
 */
public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }
        else {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/HolidaysDB", "SA", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return  connection;
        }
    }

    public static void closeConnection() {
        if(connection != null){
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
