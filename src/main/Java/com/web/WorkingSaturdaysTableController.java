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
 * Created by R500 on 15.9.2014 г..
 */
public class WorkingSaturdaysTableController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            List<WorkingSaturday> workingSaturdays  = new ArrayList<WorkingSaturday>();
            Gson gson = new Gson();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (action.equals("list")) {
                try {
                    String sort = request.getParameter("jtSorting");
                    int startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
                    int numRecordsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
                    workingSaturdays = CrudDao.getAllWorkingSaturdays(startPageIndex, numRecordsPerPage, sort);
                    int workingSaturdaysCount = CrudDao.getWorkingSaturdaysCount();

                    JsonElement element = gson.toJsonTree(workingSaturdays, new TypeToken<List<Employee>>() {}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();

                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ",\"TotalRecordCount\":" + workingSaturdaysCount + "}";

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

            else if (action.equals("create")){
                WorkingSaturday workingSaturday = new WorkingSaturday();

                workingSaturday.setDay(Integer.parseInt(request.getParameter("day")));
                workingSaturday.setMonth(Integer.parseInt(request.getParameter("month")));
                workingSaturday.setYear(Integer.parseInt(request.getParameter("year")));
                workingSaturday.setText(request.getParameter("text"));

                try {
                    workingSaturday.setWorkingSaturday_id(CrudDao.addWorkingSaturday(workingSaturday));
                    workingSaturdays.add(workingSaturday);
                    String json = gson.toJson(workingSaturday);
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

            else if (action.equals("update")){
                WorkingSaturday workingSaturday = new WorkingSaturday();

                workingSaturday.setWorkingSaturday_id(Integer.parseInt(request.getParameter("workingSaturday_id")));
                workingSaturday.setDay(Integer.parseInt(request.getParameter("day")));
                workingSaturday.setMonth(Integer.parseInt(request.getParameter("month")));
                workingSaturday.setYear(Integer.parseInt(request.getParameter("year")));
                workingSaturday.setText(request.getParameter("text"));

                CrudDao.updateWorkingSaturday(workingSaturday);

                String listData="{\"Result\":\"OK\"}";
                try {
                    response.getWriter().print(listData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (action.equals("delete")){
                try{
                    if(request.getParameter("workingSaturday_id") != null){
                        CrudDao.deleteWorkingSaturday(Integer.parseInt(request.getParameter("workingSaturday_id")));
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
