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
 * Created by R500 on 14.8.2014 г..
 */

//VacationCalendarController controls the FullCalendar from vacationCalendar.html
//    the servlet returns JSON with list of all approved vacations after they are split and the days off are excluded from them
public class VacationCalendarController extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        List<Vacation> vacations;
        if(request.getParameter("name").equals("All")) {
            vacations = CrudDao.getApprovedVacations(Integer.parseInt(request.getParameter("vacationType")));
        } else {
            vacations = CrudDao.getSelectedApprovedVacations(request.getParameter("name"), Integer.parseInt(request.getParameter("vacationType")));
        }
        JSONArray array = new JSONArray();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Days holidays = new Holidays();
        Days workingSaturdays = new WorkingSaturdays();

        for(int i = 0; i < vacations.size(); i++){
            ArrayList<String[]> splitVacation = DateHelper.excludeDaysOff(vacations.get(i).getBeginDate(), vacations.get(i).getEndDate(), holidays, workingSaturdays);
            for (int m = 0; m < splitVacation.size(); m++){
                JSONObject obj = new JSONObject();
                obj.put("title", vacations.get(i).getEmployeeName());
                obj.put("start", splitVacation.get(m)[0]);
                obj.put("end", splitVacation.get(m)[1] + "T23:59:59");
                array.put(obj);
            }
        }

        try {
            PrintWriter out = response.getWriter();
            out.println(array);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
