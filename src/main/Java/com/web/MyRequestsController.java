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
                    for (int i = 0; i < vacations.size(); i++){
                        String beginDate = vacations.get(i).getBeginDate();
                        vacations.get(i).setBeginDate(DateParser.parseDate(beginDate));
                        String endDate = vacations.get(i).getEndDate();
                        vacations.get(i).setEndDate(DateParser.parseDate(endDate));
                    }
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
                Vacation vacation = new Vacation();
                vacation.setBeginDate(request.getParameter("beginDate"));
                vacation.setEndDate(request.getParameter("endDate"));

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                boolean isBeginDateValid = false;
                try {
                    Date beginDate = sdf.parse(vacation.getBeginDate());
                    isBeginDateValid = DateCompare.CompareJavaDates(new Date(), beginDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (isBeginDateValid == true){
                    vacDaysLeft = CrudDao.getVacationDaysLeft((Integer)(session.getAttribute("employeeID")));
                    int businessDaysCount = 0;
                    try {
                        Date ParsedBeginDate = sdf.parse(vacation.getBeginDate());
                        Date ParsedEndDate = sdf.parse(vacation.getEndDate());
                        businessDaysCount = DateHelper.getBusinessDaysCount(ParsedBeginDate, ParsedEndDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
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
                        if (DateCompare.CompareDates(vacation.getBeginDate(), vacation.getEndDate()) == true && vacDaysLeft >= 0){
                            vacation.setVacationType(Integer.parseInt(request.getParameter("vacationType")));
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
                Vacation vacation = new Vacation();
                vacation.setVacationID(Integer.parseInt(request.getParameter("vacationID")));
                vacation.setBeginDate(request.getParameter("beginDate"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                boolean isBeginDateValid = false;
                try {
                    Date beginDate = sdf.parse(vacation.getBeginDate());
                    isBeginDateValid = DateCompare.CompareJavaDates(new Date(), beginDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (isBeginDateValid == true){
                    int vacationStatus = CrudDao.getVacationStatus(vacation.getVacationID());
                    if (vacationStatus == 1) {

                        vacation.setEndDate(request.getParameter("endDate"));
                        vacDaysLeft = CrudDao.getVacationDaysLeft((Integer)(session.getAttribute("employeeID")));
                        int businessDaysCount = 0;
                        try {
                            Date ParsedBeginDate = sdf.parse(vacation.getBeginDate());
                            Date ParsedEndDate = sdf.parse(vacation.getEndDate());
                            businessDaysCount = DateHelper.getBusinessDaysCount(ParsedBeginDate, ParsedEndDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
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
                            if (DateCompare.CompareDates(vacation.getBeginDate(), vacation.getEndDate()) == true && vacDaysLeft >= 0){
                                vacation.setVacationType(Integer.parseInt(request.getParameter("vacationType")));
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
                    String error = "{\"Result\":\"ERROR\",\"Message\":\"You can't delete requests that are already approved by a manager!\"}";
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
