package com.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by R500 on 17.7.2014 г..
 */
public class VacationTableController extends HttpServlet{

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            HttpSession session = request.getSession();
            List<Vacation> vacations = new ArrayList<Vacation>();

            Gson gson = new Gson();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (action.equals("list")) {
                try {
                    int startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
                    int numRecordsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
                    String sort = request.getParameter("jtSorting");
                    int vacationCount = -1;

                    if (session.getAttribute("access").equals(1)) {
                        vacations = CrudDao.getAllVacations(startPageIndex, numRecordsPerPage, sort);
                        vacationCount = CrudDao.getVacationCount();
                    } else {
                        vacations = CrudDao.getMyVacations((Integer) session.getAttribute("employeeID"), startPageIndex, numRecordsPerPage, sort);
                        vacationCount = CrudDao.getVacationCount((Integer) session.getAttribute("employeeID"));
                    }
                    JsonElement element = gson.toJsonTree(vacations, new TypeToken<List<Vacation>>() {}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();

                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ",\"TotalRecordCount\":" + vacationCount + "}";
                    response.getWriter().print(listData);
                    System.out.println(listData);
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
                Vacation vacation = new Vacation();

                vacation.setBeginDate(request.getParameter("beginDate"));
                vacation.setEndDate(request.getParameter("endDate"));
                vacation.setEmployeeID((Integer)(session.getAttribute("employeeID")));

                vacation.setEmployeeName(CrudDao.getEmployeeName((Integer) (session.getAttribute("employeeID"))));

                try {
                    vacation.setHolidayID(CrudDao.addVacation(vacation));
                    vacations.add(vacation);
                    String json = gson.toJson(vacation);
                    String listData = "{\"Result\":\"OK\",\"Record\":" + json + "}";

           //         DocumentGenerator.generateDocument(vacation.getEmployeeName(), vacation.getBeginDate(), vacation.getEndDate());

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
                Vacation vacation = new Vacation();
                vacation.setHolidayID(Integer.parseInt(request.getParameter("holidayID")));
                if (session.getAttribute("access").equals(1)){
                    vacation.setHolidayStatus(request.getParameter("holidayStatus"));
                    CrudDao.updateVacationStatus(vacation);
                } else {
                    vacation.setBeginDate(request.getParameter("beginDate"));
                    vacation.setEndDate(request.getParameter("endDate"));

                    CrudDao.updateVacationDates(vacation);
                }

                String listData="{\"Result\":\"OK\"}";
                try {
                    response.getWriter().print(listData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (action.equals("delete")){
                try{
                    if(request.getParameter("holidayID") != null){
                        String holidayID = (String)request.getParameter("holidayID");
                        CrudDao.deleteVacation(Integer.parseInt(holidayID));
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
