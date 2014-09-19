package com.web;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by R500 on 18.8.2014 г..
 */

//ForgottenPassword sends a email with a link from which the user can reset their password in the next 24 hours
public class ForgottenPassword extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String email = request.getParameter("email");
        JSONObject obj = new JSONObject();
        int employeeID = EmployeeAttributes.getEmployeeID(email);
        int access = EmployeeAttributes.getEmployeeAccessLevel(email);

//        employeeID == -1 if EmployeeAttributes.getEmployeeID(email) returns -1
//        access == 0 if EmployeeAttributes.getEmployeeAccessLevel(email) returns 0
//        this will happen if the is no such email in the database
        if(employeeID != -1 && access != 0){
            String code = RandomGenerator.generateCode();
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();
            String expiryDate = String.valueOf(date);
            CrudDao.updateForgottenPasswordCodeAndLinkExpiryDate(code, expiryDate, employeeID);
            String recipient = email;
            String subject = "Forgotten Password";
            String messageBody = "<h2>To change your password click the link below:</h2><br>" +
                    "http://localhost:8080/VacationRequestManager/forgottenPassword?email=" +
                    email + "&code=" + code + "<br><br> This link will expire in 24 hours from the moment that the email was sent or if you use it to reset your password.";
            new MailSender().sendMail(recipient, subject, messageBody, null);
            obj.put("message", "success");
        } else {
            obj.put("message", "error");
        }
        try {
            PrintWriter out = response.getWriter();
            out.println(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//programmingTestsAndStuff@gmail.com