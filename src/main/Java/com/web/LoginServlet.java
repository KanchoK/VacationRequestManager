package com.web;

import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by R500 on 16.7.2014 г..
 */
public class LoginServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        String email = request.getParameter("email")  + "@novarto.com";
        String pass = request.getParameter("password");
        String convertedPass = Utility.toSHA1(Utility.salt(pass).getBytes());

        if (LoginCheck.validate(email, convertedPass)){
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            int access = EmployeeAttributes.getEmployeeAccessLevel(email, convertedPass);
            session.setAttribute("access", access);
            int employeeID = EmployeeAttributes.getEmployeeID(email, convertedPass);
            session.setAttribute("employeeID", employeeID);
            int accountStatus = EmployeeAttributes.getAccountStatus(email, convertedPass);
            session.setAttribute("accountStatus", accountStatus);

            JSONObject obj = new JSONObject();
            obj.put("access", access);
            obj.put("accountStatus", accountStatus);

            try {
                PrintWriter out = response.getWriter();
                out.println(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject obj = new JSONObject();
            obj.put("login", "Failed");

            try {
                PrintWriter out = response.getWriter();
                out.println(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
