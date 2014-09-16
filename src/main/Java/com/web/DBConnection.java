package com.web;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by R500 on 16.7.2014 г..
 */
public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        else {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/VacationManagerDB", "SA", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return  connection;
        }

//        else {
//
//            InitialContext ctx = null;
//            DataSource ds = null;
//            try {
//                ctx = new InitialContext();
//                Context initCtx = (Context) ctx.lookup("java:comp/env");
//                ds = (DataSource) initCtx.lookup("jdbc/DSTest");
//            } catch (NamingException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                connection = ds.getConnection();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
////                Class.forName("org.hsqldb.jdbcDriver");
////                connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/VacationManagerDB", "SA", "");
//        return  connection;
//        }
//
    }


    public static void closeConnection() {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                connection = null;
            }
        }
    }
}
