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
 * Created by R500 on 14.8.2014 г..
 */
public class VacationCalendarController extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        List<Vacation> vacations;
        if(request.getParameter("name").equals("All")) {
            vacations = CrudDao.getApprovedVacations();
        } else {
            vacations = CrudDao.getSelectedApprovedVacations(request.getParameter("name"));
        }
        JSONArray array = new JSONArray();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        for(int i = 0; i < vacations.size(); i++){
            JSONObject obj = new JSONObject();
            String beginDate = vacations.get(i).getBeginDate();
            vacations.get(i).setBeginDate(DateParser.parseDate(beginDate));
            String endDate = vacations.get(i).getEndDate();
            vacations.get(i).setEndDate(DateParser.parseDate(endDate));
            obj.put("title", vacations.get(i).getEmployeeName());
            obj.put("start", vacations.get(i).getBeginDate());
            obj.put("end", vacations.get(i).getEndDate() + "T23:59:59");
            array.put(obj);
        }

        try {
            PrintWriter out = response.getWriter();
            out.println(array);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
