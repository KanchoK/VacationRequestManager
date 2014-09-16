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
 * Created by R500 on 15.9.2014 г..
 */
public class HolidaysListServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        JSONArray dates = new JSONArray();
        JSONArray text = new JSONArray();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject holidaysList = new JSONObject();

        List<Holiday> holidays = CrudDao.getAllHolidays();
        for (int i = 0; i < holidays.size(); i++){
            Holiday currentHoliday = holidays.get(i);
            String year = String.valueOf(currentHoliday.getYear());
            String month;
            if (currentHoliday.getMonth() < 10){
                month = "0" + String.valueOf(currentHoliday.getMonth());
            } else {
                month = String.valueOf(currentHoliday.getMonth());
            }
            String day;
            if (currentHoliday.getDay() < 10){
                day = "0" + String.valueOf(currentHoliday.getDay());
            } else {
                day = String.valueOf(currentHoliday.getDay());
            }
            dates.put(year + "-" + month + "-" + day);
            text.put(currentHoliday.getText());
        }

        holidaysList.put("holidaysDates", dates);
        holidaysList.put("holidaysNames", text);

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(holidaysList);
    }
}
