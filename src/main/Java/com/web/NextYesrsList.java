package com.web;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by R500 on 12.9.2014 г..
 */
public class NextYesrsList extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        JSONObject obj = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = 0;

        obj.put(String.valueOf(index), "Ежегодно");
        while(index < 10){
            obj.put(String.valueOf(calendar.get(Calendar.YEAR)), calendar.get(Calendar.YEAR));
            calendar.add(Calendar.YEAR, 1);
            index++;
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
