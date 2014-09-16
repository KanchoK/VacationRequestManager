package com.web;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by R500 on 16.9.2014 г..
 */
public class WorkingSaturdaysListServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        JSONArray dates = new JSONArray();
        JSONArray text = new JSONArray();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject workingSaturdaysList = new JSONObject();

        List<WorkingSaturday> workingSaturdays = CrudDao.getAllWorkingSaturdays();
        for (int i = 0; i < workingSaturdays.size(); i++){
            WorkingSaturday currentSaturday = workingSaturdays.get(i);
            String year = String.valueOf(currentSaturday.getYear());
            String month;
            if (currentSaturday.getMonth() < 10){
                month = "0" + String.valueOf(currentSaturday.getMonth());
            } else {
                month = String.valueOf(currentSaturday.getMonth());
            }
            String day;
            if (currentSaturday.getDay() < 10){
                day = "0" + String.valueOf(currentSaturday.getDay());
            } else {
                day = String.valueOf(currentSaturday.getDay());
            }
            dates.put(year + "-" + month + "-" + day);
            text.put(currentSaturday.getText());
        }

        workingSaturdaysList.put("workingSaturdaysDates", dates);
        workingSaturdaysList.put("workingSaturdaysNames", text);

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(workingSaturdaysList);
    }
}
