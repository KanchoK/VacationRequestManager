package com.web;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R500 on 12.8.2014 г..
 */

//ManagerListServlet sends JSON with a list of all managers for the jTable from controlPanel.html
public class ManagerListServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        JSONObject obj = new JSONObject();
        List<Employee> employees = new ArrayList<Employee>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        employees = CrudDao.getManagers();
        for (int i = 0; i < employees.size(); i++){
            obj.put(String.valueOf(employees.get(i).getEmployee_id()), employees.get(i).getEmployeeName());
        }
        try {
            String listData = "{\"Result\":\"OK\",\"Records\":" + obj + "}";
            response.getWriter().print(listData);
        } catch (Exception ex) {
            String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace() + "}";
            try {
                response.getWriter().print(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
