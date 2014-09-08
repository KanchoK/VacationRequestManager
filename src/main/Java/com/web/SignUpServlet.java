package com.web;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by R500 on 21.7.2014 г..
 */
public class SignUpServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String fName = request.getParameter("fName");
        String surname = request.getParameter("surname");
        String lName = request.getParameter("lName");
        String email = request.getParameter("email") + "@novarto.com";
        String pass = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPassword");
        String convertedPass = Utility.toSHA1(Utility.salt(pass).getBytes());
        JSONObject obj = new JSONObject();

        if (fName.trim().equals("") || surname.trim().equals("") || lName.trim().equals("") || email.trim().equals("") || pass.trim().equals("")){
            obj.put("message", "You must fill all fields.");
        } else if (!(pass.equals(confirmPass))) {
            obj.put("message", "The Password must be the same as the Confirm Password field.");
        } else if (CrudDao.isEmailTaken(email)) {
            obj.put("message", "The Email you used is already taken.");
        } else if (!(email.matches("[a-zA-Z][a-zA-Z0-9_]*[\\.]?[a-zA-Z0-9_]*[a-zA-Z0-9]@novarto\\.com"))) {
            obj.put("message", "Your e-mail must start with a letter and can only have letters, numbers, unrepeatable underscores(_) and only one dot(.)");
        } else {
            Employee employee = new Employee();

            employee.setfName(fName);
            employee.setSurname(surname);
            employee.setlName(lName);
            employee.setEmployeeName(fName + " "+ surname + " " + lName);
            employee.setEmail(email);
            employee.setPassword(convertedPass);
            employee.setAccessLevel(0);
            employee.setAccountStatus(1);
            employee.setVacationDaysLeft(25);
            CrudDao.addEmployee(employee);

            obj.put("message", "");
        }

        try {
            PrintWriter out = response.getWriter();
            out.println(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
