package com.web;


import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by R500 on 16.9.2014 г..
 */
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
    }

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
