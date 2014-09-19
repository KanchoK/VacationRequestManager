package com.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R500 on 12.9.2014 г..
 */
//HolidaysTableController controls the Holidays jTable from the holidaysManager.html
public class HolidaysTableController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            List<Holiday> holidays = new ArrayList<Holiday>();
            Gson gson = new Gson();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

//            list all holidays
            if (action.equals("list")) {
                try {
                    String sort = request.getParameter("jtSorting");
                    int startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
                    int numRecordsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
                    holidays = CrudDao.getAllHolidays(startPageIndex, numRecordsPerPage, sort);
                    int holidaysCount = CrudDao.getHolidaysCount();

                    JsonElement element = gson.toJsonTree(holidays, new TypeToken<List<Employee>>() {}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();

                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ",\"TotalRecordCount\":" + holidaysCount + "}";

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

//            create new holiday
            else if (action.equals("create")){
                Holiday holiday = new Holiday();

                holiday.setDay(Integer.parseInt(request.getParameter("day")));
                holiday.setMonth(Integer.parseInt(request.getParameter("month")));
                holiday.setYear(Integer.parseInt(request.getParameter("year")));
                holiday.setText(request.getParameter("text"));

                try {
                    holiday.setHoliday_id(CrudDao.addHoliday(holiday));
                    holidays.add(holiday);
                    String json = gson.toJson(holiday);
                    String listData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
                    try {
                        response.getWriter().print(listData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception ex){
                    String error = "{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace()+"}";
                    try {
                        response.getWriter().print(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

//            update the selected holiday
            else if (action.equals("update")){
                Holiday holiday = new Holiday();

                holiday.setHoliday_id(Integer.parseInt(request.getParameter("holiday_id")));
                holiday.setDay(Integer.parseInt(request.getParameter("day")));
                holiday.setMonth(Integer.parseInt(request.getParameter("month")));
                holiday.setYear(Integer.parseInt(request.getParameter("year")));
                holiday.setText(request.getParameter("text"));

                CrudDao.updateHoliday(holiday);

                String listData="{\"Result\":\"OK\"}";
                try {
                    response.getWriter().print(listData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            delete the selected holiday
            else if (action.equals("delete")){
                try{
                    if(request.getParameter("holiday_id") != null){
                        CrudDao.deleteHoliday(Integer.parseInt(request.getParameter("holiday_id")));
                        String listData="{\"Result\":\"OK\"}";
                        response.getWriter().print(listData);
                    }
                }catch(Exception ex){
                    String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
                    try {
                        response.getWriter().print(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
