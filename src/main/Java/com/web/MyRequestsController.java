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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by R500 on 17.7.2014 г..
 */
public class MyRequestsController extends HttpServlet{

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            HttpSession session = request.getSession();
            List<Vacation> vacations = new ArrayList<Vacation>();
            int vacDaysLeft = -1;
            Gson gson = new Gson();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (action.equals("list")) {
                try {
                    int startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
                    int numRecordsPerPage = Integer.parseInt(request.getParameter("jtPageSize"));
                    String sort = request.getParameter("jtSorting");
                    int vacationCount = -1;
                    vacations = CrudDao.getMyVacations((Integer) session.getAttribute("employeeID"), startPageIndex, numRecordsPerPage, sort);
                    vacationCount = CrudDao.getVacationCount((Integer) session.getAttribute("employeeID"));
                    JsonElement element = gson.toJsonTree(vacations, new TypeToken<List<Vacation>>() {}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();

                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ",\"TotalRecordCount\":" + vacationCount + "}";
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

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                boolean isBeginDateValid = false;
                try {
                    Date beginDate = sdf.parse(request.getParameter("beginDate"));
                    isBeginDateValid = DateCompare.CompareJavaDates(new Date(), beginDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (isBeginDateValid){
                    vacDaysLeft = CrudDao.getVacationDaysLeft((Integer)(session.getAttribute("employeeID")));
                    int vacationType = Integer.parseInt(request.getParameter("vacationType"));
                    int businessDaysCount = 0;
                    if (vacationType == 1){
                        try {
                            Date ParsedBeginDate = sdf.parse(request.getParameter("beginDate"));
                            Date ParsedEndDate = sdf.parse(request.getParameter("endDate"));
                            businessDaysCount = DateHelper.getBusinessDaysCount(ParsedBeginDate, ParsedEndDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (businessDaysCount == -1){
                        String error = "{\"Result\":\"ERROR\",\"Message\":\"Initial dates should both be working days!\"}";
                        try {
                            response.getWriter().print(error);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        vacDaysLeft -= businessDaysCount;
                        if (DateCompare.CompareDates(request.getParameter("beginDate"), request.getParameter("endDate")) && (vacDaysLeft >= 0 || vacationType == 2)){
                            Vacation vacation = new Vacation();
                            vacation.setBeginDate(DateParser.parseDate(request.getParameter("beginDate")));
                            vacation.setEndDate(DateParser.parseDate(request.getParameter("endDate")));
                            vacation.setVacationType(vacationType);
                            if (vacationType == 2){
                                vacation.setVacationStatus(2);
                            } else {
                                vacation.setVacationStatus(1);
                            }
                            vacation.setRequestText(request.getParameter("requestText"));
                            vacation.setEmployeeID((Integer)(session.getAttribute("employeeID")));
                            vacation.setMyManager(CrudDao.getManagerID((Integer)(session.getAttribute("employeeID"))));
                            vacation.setEmployeeName(CrudDao.getEmployeeName((Integer)(session.getAttribute("employeeID"))));
                            try {
                                vacation.setVacationID(CrudDao.addVacation(vacation));
                                vacations.add(vacation);
                                String json = gson.toJson(vacation);
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
                        } else {
                            String error;
                            if (vacDaysLeft >= 0) {
                                error = "{\"Result\":\"ERROR\",\"Message\":\"The begin date can't be after the end date!\"}";
                            } else {
                                error = "{\"Result\":\"ERROR\",\"Message\":\"You don't have enough vacation days left!\"}";
                            }
                            try {
                                response.getWriter().print(error);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    String error = "{\"Result\":\"ERROR\",\"Message\":\"You cannot create vacation request if its begin date has passed!\"}";
                    try {
                        response.getWriter().print(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            else if (action.equals("update")){

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                boolean isBeginDateValid = false;
                try {
                    Date beginDate = sdf.parse(request.getParameter("beginDate"));
                    isBeginDateValid = DateCompare.CompareJavaDates(new Date(), beginDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (isBeginDateValid){
                    int vacationStatus = CrudDao.getVacationStatus(Integer.parseInt(request.getParameter("vacationID")));
                    int vacationType = CrudDao.getVacationType(Integer.parseInt(request.getParameter("vacationID")));
                    if (vacationStatus == 1 || vacationType == 2) {
                        vacDaysLeft = CrudDao.getVacationDaysLeft((Integer)(session.getAttribute("employeeID")));
                        int businessDaysCount = 0;
                        if (vacationType == 1) {
                            try {
                                Date ParsedBeginDate = sdf.parse(request.getParameter("beginDate"));
                                Date ParsedEndDate = sdf.parse(request.getParameter("endDate"));
                                businessDaysCount = DateHelper.getBusinessDaysCount(ParsedBeginDate, ParsedEndDate);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (businessDaysCount == -1){
                            String error = "{\"Result\":\"ERROR\",\"Message\":\"Initial dates should both be working days!\"}";
                            try {
                                response.getWriter().print(error);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            vacDaysLeft -= businessDaysCount;
                            if (DateCompare.CompareDates(request.getParameter("beginDate"), request.getParameter("endDate")) && (vacDaysLeft >= 0 || vacationType == 2)){
                                Vacation vacation = new Vacation();
                                vacation.setVacationID(Integer.parseInt(request.getParameter("vacationID")));
                                vacation.setBeginDate(DateParser.parseDate(request.getParameter("beginDate")));
                                vacation.setEndDate(DateParser.parseDate(request.getParameter("endDate")));
                                vacation.setRequestText(request.getParameter("requestText"));

                                CrudDao.updateVacationDates(vacation);

                                String listData = "{\"Result\":\"OK\"}";
                                try {
                                    response.getWriter().print(listData);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                String error;
                                if (vacDaysLeft >= 0) {
                                    error = "{\"Result\":\"ERROR\",\"Message\":\"The begin date must be before the end date!\"}";
                                } else {
                                    error = "{\"Result\":\"ERROR\",\"Message\":\"You don't have enough vacation days left!\"}";
                                }
                                try {
                                    response.getWriter().print(error);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        String error = "{\"Result\":\"ERROR\",\"Message\":\"You can't change the requests that are already viewed by a manager!\"}";
                        try {
                            response.getWriter().print(error);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    String error = "{\"Result\":\"ERROR\",\"Message\":\"You cannot update the request if its begin date has passed!\"}";
                    try {
                        response.getWriter().print(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if (action.equals("delete")){
                int vacationStatus = CrudDao.getVacationStatus(Integer.parseInt(request.getParameter("vacationID")));
                if (vacationStatus != 2) {
                    try {
                        if (request.getParameter("vacationID") != null) {
                            CrudDao.deleteVacation(Integer.parseInt(request.getParameter("vacationID")));
                            String listData = "{\"Result\":\"OK\"}";
                            response.getWriter().print(listData);
                        }
                    } catch (Exception ex) {
                        String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace().toString() + "}";
                        try {
                            response.getWriter().print(error);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    String error = "{\"Result\":\"ERROR\",\"Message\":\"You can't delete requests that are already approved!\"}";
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
