package com.web;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R500 on 8.9.2014 г..
 */
public class EmployeeListServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        JSONArray array = new JSONArray();
        List<Employee> employees = new ArrayList<Employee>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        employees = CrudDao.getEmployeesName();
        for (int i = 0; i < employees.size(); i++){
            JSONObject rowObj = new JSONObject();
            rowObj.put("name", employees.get(i).getEmployeeName());
            array.put(rowObj);
        }

        JSONObject employeesList = new JSONObject();
        employeesList.put("employeesList", array);
        try {
            PrintWriter out = response.getWriter();
            out.println(employeesList);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
