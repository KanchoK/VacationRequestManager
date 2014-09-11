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
import java.util.*;

/**
 * Created by R500 on 11.8.2014 г..
 */
public class RequestManagerController extends HttpServlet{

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
                    int requstCount = -1;
                    vacations = CrudDao.getVacationRequests((Integer)session.getAttribute("employeeID"), startPageIndex, numRecordsPerPage, sort);
//                    vacations = CrudDao.getAllVacations(startPageIndex, numRecordsPerPage, sort);
                    requstCount = CrudDao.getRequestCount((Integer)session.getAttribute("employeeID"));
                    JsonElement element = gson.toJsonTree(vacations, new TypeToken<List<Vacation>>() {}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();

                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ",\"TotalRecordCount\":" + requstCount + "}";
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

            else if (action.equals("update")){
                Vacation vacation = new Vacation();
                vacation.setVacationID(Integer.parseInt(request.getParameter("vacationID")));
                int vacationType = CrudDao.getVacationType(Integer.parseInt(request.getParameter("vacationID")));

                boolean isVacationNotBegan = false;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date beginDate = sdf.parse(CrudDao.getVacationBeginDate(Integer.parseInt(request.getParameter("vacationID"))));
                    isVacationNotBegan = DateCompare.CompareJavaDates(new Date(), beginDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (isVacationNotBegan && vacationType != 2){
                    vacation.setResponseText(request.getParameter("responseText"));
                    vacation.setVacationStatus(Integer.parseInt(request.getParameter("vacationStatus")));

                    int vacDaysLeft = -1;

                    if(vacation.getVacationStatus() != CrudDao.getVacationStatus(vacation.getVacationID())){
                        Map info = CrudDao.getInfoForDocument(vacation.getVacationID());
                        String employeeName = String.valueOf(info.get("employeeName"));
                        vacDaysLeft = (Integer)info.get("vacationDaysLeft");
                        String vacationDaysLeft = String.valueOf(vacDaysLeft);
                        String beginDate = DateParser.parseDateToBGFomat(String.valueOf(info.get("beginDate")));
                        String endDate = DateParser.parseDateToBGFomat(String.valueOf(info.get("endDate")));
                        String vacationTypeString = "Полагаем годишен отпуск";
                        int businessDaysCount = 0;
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                            Date ParsedBeginDate = sdf.parse(beginDate);
                            Date ParsedEndDate = sdf.parse(endDate);
                            businessDaysCount = DateHelper.getBusinessDaysCount(ParsedBeginDate, ParsedEndDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        vacDaysLeft -= businessDaysCount;
                        if(vacation.getVacationStatus() == 2 && vacDaysLeft >= 0){
                            CrudDao.updateVacationDaysLeft(businessDaysCount, EmployeeAttributes.getEmployeeID(vacation.getVacationID()));

                            String allDays = String.valueOf(businessDaysCount);

                            PDFGenerator.generateVacationRequestPDF(employeeName, vacationDaysLeft, beginDate, endDate, allDays, vacationTypeString);

                            String recipient = "programmingTestsAndStuff@gmail.com";
                            String subject = "Молба за отпуск";
                            String message = "<h3>Молба за отпуска на " + employeeName + ":</h3>";
                            String attachment = "C:/Users/R500/IdeaProjects/VacationRequestManager/src/main/webapp/documents/VacationRequest.pdf";
                            MailSender mailSender = new MailSender();
                            mailSender.sendMail(recipient, subject, message, attachment);
                        }
                        if (CrudDao.getVacationStatus(vacation.getVacationID()) == 2 && (vacation.getVacationStatus() == 3 || vacation.getVacationStatus() == 1)){
                            CrudDao.updateVacationDaysLeft(-businessDaysCount, EmployeeAttributes.getEmployeeID(vacation.getVacationID()));
                        }
                        if (vacDaysLeft < 0){
                            String error = "{\"Result\":\"ERROR\",\"Message\":\"Not enough vacation days left for this vacation!\"}";
                            try {
                                response.getWriter().print(error);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            CrudDao.updateVacationStatus(vacation);
                            String listData="{\"Result\":\"OK\"}";
                            try {
                                response.getWriter().print(listData);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        if (!(vacation.getResponseText().equals(CrudDao.getVacationResponseText(vacation.getVacationID())))){
                            CrudDao.updateVacationResponseText(vacation.getResponseText(), vacation.getVacationID());
                            String listData="{\"Result\":\"OK\"}";
                            try {
                                response.getWriter().print(listData);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String error = "{\"Result\":\"ERROR\",\"Message\":\"You haven't updated anything!\"}";
                            try {
                                response.getWriter().print(error);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    String error = "";
                    if (vacationType == 2){
                        error = "{\"Result\":\"ERROR\",\"Message\":\"You cannot update requests of this type!\"}";
                    } else {
                        error = "{\"Result\":\"ERROR\",\"Message\":\"You cannot update the request if its begin date has passed!\"}";
                    }
                    try {
                        response.getWriter().print(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if (action.equals("delete")){
                int vacationStatus = CrudDao.getVacationStatus(Integer.parseInt(request.getParameter("vacationID")));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                boolean isVacationOver = false;
                try {
                    Date endDate = sdf.parse(CrudDao.getVacationEndDate(Integer.parseInt(request.getParameter("vacationID"))));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(endDate);
                    calendar.add(Calendar.DATE, 1);
                    Date endDatePlusOne = calendar.getTime();
                    isVacationOver = DateCompare.CompareJavaDates(endDatePlusOne, new Date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (vacationStatus != 2 || isVacationOver) {
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
                    String error = "{\"Result\":\"ERROR\",\"Message\":\"You can't delete approved requests before the end date of the vacation!\"}";
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
