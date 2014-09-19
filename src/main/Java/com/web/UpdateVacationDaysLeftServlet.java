package com.web;


import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by R500 on 16.9.2014 г..
 */

//UpdateVacationDaysLeftServlet updates the vacation days left of all employees by saving up to daysToSave days and adds daysToAdd
//    where daysToSave and daysToAdd are set in the servlet
//    this update is meant to happen only once a year so to execute it the admin need to use the button in the control panel bottom and he needs to provide his password to confirm the update
public class UpdateVacationDaysLeftServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String pass = request.getParameter("password");
        String convertedPass = Utility.toSHA1(Utility.salt(pass).getBytes());
        HttpSession session = request.getSession();
        String passFromDB = CrudDao.getPassword((Integer) session.getAttribute("employeeID"));
        JSONObject obj = new JSONObject();

        if (convertedPass.equals(passFromDB)){
            executeUpdate();
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

//    executeUpdate actually updates the vacation days left of all employees
//    this method is called by the servlet only if the password given by the admin is correct
    public void executeUpdate(){
        List<Employee> employees = CrudDao.getEmployeesVacationDaysLeft();
        int daysToSave = 10;
        int daysToAdd = 25;
        for (int i = 0; i < employees.size(); i++){
            Employee currentEmployee = employees.get(i);
            int currentEmployeeVacationDaysLeft = currentEmployee.getVacationDaysLeft();
            int updatedEmployeeVacationDaysLeft;
            if (currentEmployeeVacationDaysLeft > daysToSave){
                updatedEmployeeVacationDaysLeft = daysToSave + daysToAdd;
            } else {
                updatedEmployeeVacationDaysLeft = currentEmployeeVacationDaysLeft + daysToAdd;
            }
            CrudDao.updateVacationDaysLeftEveryYear(updatedEmployeeVacationDaysLeft, currentEmployee.getEmployee_id());
        }
    }
}
